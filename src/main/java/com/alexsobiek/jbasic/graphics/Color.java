package com.alexsobiek.jbasic.graphics;

/**
 * A quick note about color handling:
 * Because we are storing characters and their foreground and background colors in just 3 bytes, we can only
 * use 8-bit color. To achieve this, we only retain the first 3 bits of R and G, and the first 2 bits of B from
 * 24 bit color.
 *
 * Bit    7  6  5  4  3  2  1  0
 * Data   R  R  R  G  G  G  B  B
 *
 * TODO: White (255) does not translate to a perfect white, but instead to r=255, g=224, b=192
 */
public class Color {
    /**
     * Returns 8-bit color byte
     * @param r Red value
     * @param b Blue value
     * @param g Green value
     * @return byte
     */
    public byte toByte(int r, int g, int b) {
        return (byte) (((r >> 5) << 5) | ((g >> 5) << 2) | (b >> 6));
    }

    /**
     * Returns 8-bit color byte from Java Color object
     * @param color Java Color
     * @return byte
     */
    public byte fromJColor(java.awt.Color color) {
        return toByte(color.getRed(), color.getGreen(), color.getBlue());
    }


    /**
     * Converts 8-bit color integer into java.awt.Color object
     * @param rgb 8-bit color
     * @return java.awt.Color
     */
    public java.awt.Color from(int rgb) {
        rgb = rgb << 24;
        int r = (rgb >> 29) << 29;
        int g = (((rgb << 3) >> 29) << 21);
        int b = (((rgb << 6)) >>> 16);
        rgb = (r | g | b) >>> 8;
        return new java.awt.Color(rgb);
    }

}
