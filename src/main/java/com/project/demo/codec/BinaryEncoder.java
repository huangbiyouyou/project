package com.project.demo.codec;


public interface BinaryEncoder extends Encoder {

    byte[] encode(byte[] var1) throws EncoderException;
}
