package com.kisahcode.fundamentalandroid

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Unit tests for the MainViewModel class.
 */
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboidModel: CuboidModel

    // Dummy dimensions for testing
    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    // Expected results for testing
    private val dummyVolume = 504.0
    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0

    /**
     * Set up before each test case.
     */
    @Before
    fun before() {
        // Mock the CuboidModel
        cuboidModel = mock(CuboidModel::class.java)
        mainViewModel = MainViewModel(cuboidModel)
    }

    /**
     * Test case to verify volume calculation.
     */
    @Test
    fun testVolume() {
        // Create a new instance of MainViewModel and CuboidModel
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        // Save dummy dimensions
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        // Get calculated volume
        val volume = mainViewModel.getVolume()
        // Assert volume calculation
        assertEquals(dummyVolume, volume, 0.0001)
    }

    /**
     * Test case to verify circumference calculation.
     */
    @Test
    fun testCircumference() {
        // Create a new instance of MainViewModel and CuboidModel
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        // Save dummy dimensions
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        // Get calculated circumference
        val circumference = mainViewModel.getCircumference()
        // Assert circumference calculation
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    /**
     * Test case to verify surface area calculation.
     */
    @Test
    fun testSurfaceArea() {
        // Create a new instance of MainViewModel and CuboidModel
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        // Save dummy dimensions
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        // Get calculated surface area
        val surfaceArea = mainViewModel.getSurfaceArea()
        // Assert surface area calculation
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

    /**
     * Test case using mockito to mock volume calculation.
     */
    @Test
    fun testMockVolume() {
        // Set up Mockito mock behavior for volume calculation
        `when`(mainViewModel.getVolume()).thenReturn(dummyVolume)
        // Get volume using mock behavior
        val volume = mainViewModel.getVolume()
        // Verify that CuboidModel's getVolume method is called
        verify(cuboidModel).getVolume()
        // Assert volume calculation using mocked value
        assertEquals(dummyVolume, volume, 0.0001)
    }

    /**
     * Test case using mockito to mock circumference calculation.
     */
    @Test
    fun testMockCircumference() {
        // Set up Mockito mock behavior for circumference calculation
        `when`(mainViewModel.getCircumference()).thenReturn(dummyCircumference)
        // Get circumference using mock behavior
        val circumference = mainViewModel.getCircumference()
        // Verify that CuboidModel's getCircumference method is called
        verify(cuboidModel).getCircumference()
        // Assert circumference calculation using mocked value
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    /**
     * Test case using mockito to mock surface area calculation.
     */
    @Test
    fun testMockSurfaceArea() {
        // Set up Mockito mock behavior for surface area calculation
        `when`(mainViewModel.getSurfaceArea()).thenReturn(dummySurfaceArea)
        // Get surface area using mock behavior
        val surfaceArea = mainViewModel.getSurfaceArea()
        // Verify that CuboidModel's getSurfaceArea method is called
        verify(cuboidModel).getSurfaceArea()
        // Assert surface area calculation using mocked value
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }
}