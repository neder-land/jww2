package com.github.neder_land.jww2.util;

public interface ObjectSerializable<T> {
    /**
     * Serialize the specific object into an object,containing
     * all information that is necessary for reconstructing the object.
     * <br />It is recommended that the shorter, the better.
     * <br />The {@link ObjectSerializable} implementations must ensure that<br />
     * {@code if(o1.equals(o2)) assert o1.serialize().equals(o2.serialize());}
     * @return an object containing the information about the object
     */
    T serialize();
}
