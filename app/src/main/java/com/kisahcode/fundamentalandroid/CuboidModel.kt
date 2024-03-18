package com.kisahcode.fundamentalandroid

/**
 * This class represents a cuboid model with width, height, and length dimensions.
 * It provides methods to calculate surface area, circumference, and to save dimensions.
 */
class CuboidModel {
    private var width = 0.0 // The width of the cuboid.
    private var height = 0.0 // The height of the cuboid.
    private var length = 0.0 // The length of the cuboid.

    /**
     * Calculates and returns the volume of the cuboid.
     * @return The volume of the cuboid.
     */
    fun getVolume(): Double = width * length * height // Volume of the cuboid.

    /**
     * Calculates and returns the surface area of the cuboid.
     * @return The surface area of the cuboid.
     */
    fun getSurfaceArea(): Double {
        val wl = width * length // Area of the side with width and length.
        val wh = width * height // Area of the side with width and height.
        val lh = length * height // Area of the side with length and height.
        return 2 * (wl + wh + lh) // Total surface area of the cuboid.
    }

    /**
     * Calculates and returns the circumference of the cuboid.
     * @return The circumference of the cuboid.
     */
    fun getCircumference(): Double = 4 * (width + length + height) // Total circumference of the cuboid.

    /**
     * Saves the dimensions of the cuboid.
     * @param width The width of the cuboid.
     * @param length The length of the cuboid.
     * @param height The height of the cuboid.
     */
    fun save(width: Double, length: Double, height: Double) {
        this.width = width // Saves the width of the cuboid.
        this.length = length // Saves the length of the cuboid.
        this.height = height // Saves the height of the cuboid.
    }
}