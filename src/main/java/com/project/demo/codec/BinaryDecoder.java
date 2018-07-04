package com.project.demo.codec;



public interface BinaryDecoder extends Decoder{
    byte[] decode(byte[] var1) throws DecoderException;
}
