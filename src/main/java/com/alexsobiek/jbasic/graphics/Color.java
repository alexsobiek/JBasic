package com.alexsobiek.jbasic.graphics;

/**
 * A quick note about color handling:
 * Because we are storing characters and their foreground and background colors in just 3 bytes, we can only
 * use 8-bit color. To achieve this, we only retain the first 3 bits of R and G, and the first 2 bits of B from
 * 24 bit color.
 *
 * Bit    7  6  5  4  3  2  1  0
 * Data   R  R  R  G  G  G  B  B
 */
public class Color {
    public static void main(String[] args) {
        byte a = toByte(0xb2, 0x66, 0xFF); // 178, 102, 255
        System.out.println(from(a)); // 160, 96, 192

        byte b = toByte(0x97, 0xC0, 0x60); // 151, 102, 96
        System.out.println(from(b)); // 255, 192, 64
    }

    /**
     * Returns 8-bit color byte
     * @param r Red value
     * @param b Blue value
     * @param g Green value
     * @return byte
     */
    public static byte toByte(int r, int g, int b) {
        return (byte) (((r >> 5) << 5) | ((g >> 5) << 2) | (b >> 6));
    }


    /**
     * Converts 8-bit color integer into java.awt.Color object
     * @param rgb 8-bit color
     * @return java.awt.Color
     */
    public static java.awt.Color from(int rgb) {
        rgb = rgb << 24;
        int r = (rgb >> 29) << 29;
        int g = (((rgb << 3) >> 29) << 21);
        int b = (((rgb << 6)) >>> 16);
        rgb = (r | g | b) >>> 8;
        return new java.awt.Color(rgb);
    }

}
