package com.kisahcode.fundamentalandroid

/**
 * ViewModel class for the MainFragment. It interacts with the CuboidModel to perform calculations
 * and save dimensions.
 * @param cuboidModel The CuboidModel instance to perform calculations.
 */
class MainViewModel(private val cuboidModel: CuboidModel) {

    /**
     * Retrieves the circumference of the cuboid from the associated CuboidModel.
     * @return The circumference of the cuboid.
     */
    fun getCircumference() = cuboidModel.getCircumference()

    /**
     * Retrieves the surface area of the cuboid from the associated CuboidModel.
     * @return The surface area of the cuboid.
     */
    fun getSurfaceArea() = cuboidModel.getSurfaceArea()

    /**
     * Retrieves the volume of the cuboid from the associated CuboidModel.
     * @return The volume of the cuboid.
     */
    fun getVolume() = cuboidModel.getVolume()

    /**
     * Saves the dimensions of the cuboid using the associated CuboidModel.
     * @param w The width of the cuboid.
     * @param l The length of the cuboid.
     * @param h The height of the cuboid.
     */
    fun save(w: Double, l: Double, h: Double) {
        cuboidModel.save(w, l, h)
    }
}
