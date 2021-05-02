package com.github.neder_land.jww2.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class SerdeHelper {
    public static SerializationBuilder serializer() {
        return new SerializationBuilder();
    }

    public static DeserializationBuilder deserializer(JsonObject jo) {
        Preconditions.checkArgument(jo != null && !jo.keySet().isEmpty());
        return new DeserializationBuilder(jo);
    }

    public static<T> JsonArray serializeCollection(Collection<T> collection, Function<T,JsonElement> serializer) {
        Preconditions.checkArgument(collection != null && serializer != null);
        if(collection.isEmpty()) {
            return new JsonArray(0);
        }
        JsonArray array = new JsonArray(collection.size());
        for(T t : collection) {
            array.add(serializer.apply(t));
        }
        return array;
    }

    public static class SerializationBuilder {
        private final JsonObject closure = new JsonObject();

        public<T> SerializationBuilder add(String key, Collection<T> coll, Function<T,JsonElement> serializer) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            closure.add(key,SerdeHelper.serializeCollection(coll,serializer));
            return this;
        }

        public SerializationBuilder addEmptyObject(String key) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            closure.add(key,new JsonObject());
            return this;
        }

        public ChildSerializationBuilder<? extends SerializationBuilder> beginObject(String key) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            return new ChildSerializationBuilder<>(this,key);
        }

        public SerializationBuilder add(String key, JsonElement value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(value != null);
            Preconditions.checkArgument(!closure.has(key));
            closure.add(key, value);
            return this;
        }

        public SerializationBuilder add(String key,String value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(value != null);
            Preconditions.checkArgument(!closure.has(key));
            closure.addProperty(key,value);
            return this;
        }

        public SerializationBuilder add(String key,Number value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(value != null);
            Preconditions.checkArgument(!closure.has(key));
            closure.addProperty(key,value);
            return this;
        }

        public SerializationBuilder add(String key,boolean value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            closure.addProperty(key,value);
            return this;
        }

        public SerializationBuilder add(String key,char value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            closure.addProperty(key,value);
            return this;
        }

        public SerializationBuilder remove(String key) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(closure.has(key));
            closure.remove(key);
            return this;
        }

        public JsonObject build() {
            return closure;
        }

    }

    public static class ChildSerializationBuilder<T extends SerializationBuilder> extends SerializationBuilder {

        private final T parent;
        private final String key;

        public ChildSerializationBuilder(T serializationBuilder,String key) {
            this.parent = serializationBuilder;
            this.key = key;
        }

        private final JsonObject closure = new JsonObject();

        public<V> ChildSerializationBuilder<T> add(String key, Collection<V> coll, Function<V,JsonElement> serializer) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(coll != null);
            Preconditions.checkArgument(serializer != null);
            Preconditions.checkArgument(!closure.has(key));
            return this;
        }

        public ChildSerializationBuilder<T> addEmptyObject(String key) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            closure.add(key,new JsonObject());
            return this;
        }

        public ChildSerializationBuilder<ChildSerializationBuilder<T>> beginObject(String key) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            return new ChildSerializationBuilder<>(this,key);
        }

        public ChildSerializationBuilder<T> add(String key, JsonElement value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(value != null);
            Preconditions.checkArgument(!closure.has(key));
            closure.add(key, value);
            return this;
        }

        public ChildSerializationBuilder<T> add(String key,String value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(value != null);
            Preconditions.checkArgument(!closure.has(key));
            closure.addProperty(key,value);
            return this;
        }

        public ChildSerializationBuilder<T> add(String key,Number value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(value != null);
            Preconditions.checkArgument(!closure.has(key));
            closure.addProperty(key,value);
            return this;
        }

        public ChildSerializationBuilder<T> add(String key,boolean value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            closure.addProperty(key,value);
            return this;
        }

        public ChildSerializationBuilder<T> add(String key,char value) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(!closure.has(key));
            closure.addProperty(key,value);
            return this;
        }

        public ChildSerializationBuilder<T> remove(String key) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(closure.has(key));
            closure.remove(key);
            return this;
        }

        public T finish() {
            parent.add(key,closure);
            return parent;
        }
    }

    public static class DeserializationBuilder {
        private final JsonObject closure;
        private Function<JsonObject,JsonObject> operations = Function.identity();
        public DeserializationBuilder(JsonObject jo) {
            Preconditions.checkArgument(jo != null);
            this.closure = jo;
        }

        public<T> DeserializationBuilder value(String key, Function<JsonElement, T> deserialization, Consumer<T> operation) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(closure.has(key));
            Preconditions.checkArgument(deserialization != null);
            Preconditions.checkArgument(operation != null);
            operations = operations.andThen((closure) -> {
                operation.accept(deserialization.apply(closure.get(key)));
                return closure;
            });
            return this;
        }

        public DeserializationBuilder stringValue(String key, Consumer<String> operation) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(closure.has(key));
            Preconditions.checkArgument(operation != null);
            operations = operations.andThen(closure -> {
                operation.accept(closure.get(key).getAsString());
                return closure;
            });
            return this;
        }

        public DeserializationBuilder booleanValue(String key, Consumer<Boolean> operation) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(closure.has(key));
            Preconditions.checkArgument(operation != null);
            operations = operations.andThen(closure -> {
                operation.accept(closure.get(key).getAsBoolean());
                return closure;
            });
            return this;
        }

        public DeserializationBuilder charValue(String key, Consumer<Character> operation) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(closure.has(key));
            Preconditions.checkArgument(operation != null);
            operations = operations.andThen(closure -> {
                operation.accept(closure.get(key).getAsCharacter());
                return closure;
            });
            return this;
        }

        public DeserializationBuilder intValue(String key, Consumer<Integer> operation) {
            return value(key, ComplexFunctions.chain(JsonElement::getAsInt, Integer::valueOf), operation);
        }

        public DeserializationBuilder byteValue(String key, Consumer<Byte> operation) {
            return value(key, ComplexFunctions.chain(JsonElement::getAsByte, Byte::valueOf), operation);
        }

        public DeserializationBuilder shortValue(String key, Consumer<Short> operation) {
            return value(key, ComplexFunctions.chain(JsonElement::getAsShort, Short::valueOf), operation);
        }

        public DeserializationBuilder longValue(String key, Consumer<Long> operation) {
            return value(key, ComplexFunctions.chain(JsonElement::getAsLong, Long::valueOf), operation);
        }

        public DeserializationBuilder floatValue(String key, Consumer<Float> operation) {
            return value(key, ComplexFunctions.chain(JsonElement::getAsFloat, Float::valueOf), operation);
        }

        public DeserializationBuilder doubleValue(String key, Consumer<Double> operation) {
            return value(key, ComplexFunctions.chain(JsonElement::getAsDouble,Double::valueOf), operation);
        }

        public<T,C extends Collection<T>> DeserializationBuilder collectionValue(String key,
                                                                                 Function<Integer,C> collectionGenerator,
                                                                                 Function<JsonElement, T> deserializer,
                                                                                 Consumer<C> operation) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
            Preconditions.checkArgument(closure.has(key));
            Preconditions.checkArgument(collectionGenerator != null);
            Preconditions.checkArgument(deserializer != null);
            Preconditions.checkArgument(operation != null);
            operations = operations.andThen(closure -> {
                JsonArray array = closure.getAsJsonArray(key);
                C collection = collectionGenerator.apply(array.size());
                for(JsonElement e : array) {
                    collection.add(deserializer.apply(e));
                }
                operation.accept(collection);
                return closure;
            });
            return this;
        }

        public void finish() {
            operations.apply(closure);
        }

        public Function<JsonObject, Void> getFunction() {
            return operations.andThen(unused->null);
        }
    }
}
