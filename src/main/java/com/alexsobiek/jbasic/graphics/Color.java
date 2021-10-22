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
        // System.out.println(toByte(255, 255, 255) & 0xFF);
        // System.out.println(Integer.toString(toInt(255, 255, 255), 2));
        int c = toByte(7, 223, 95) & 0xFF; // 95 = 01011111, 223 = 11011111
        // System.out.println(Integer.toBinaryString(c)); // 01011001
        // System.out.println(from(144));
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
        /**
         * Java Color is 24 bit, so we have to take the first 3 bits of R and B, first two bits of g, and pad them each
         *   R      G     B
         * 1 1 1  1 1 1  1 1
         * R = ((rgb  >> 5) << 21) | (0x1F << 16)
         * G = (((rgb >> 2) << 29) >>> 16) | (0x1F << 8)
         * B = (((rgb << 30)) >>> 24) | 0x3F
         */
        rgb = (((rgb  >> 5) << 21) | (0x1F << 16) | (((rgb >> 2) << 29) >>> 16) | (0x1F << 8) | (((rgb << 30)) >>> 24) | 0x3F);
        System.out.println(Integer.toHexString(rgb));
        return new java.awt.Color(rgb);
    }

}
