package com.kisahcode.fundamentalandroid.data.response

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the response for a restaurant.
 * @param restaurant The restaurant details.
 * @param error Boolean value indicating if an error occurred.
 * @param message Error message if applicable.
 */
data class RestaurantResponse(

	@field:SerializedName("restaurant")
	val restaurant: Restaurant,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

/**
 * Data class representing a restaurant.
 * @param customerReviews List of customer reviews for the restaurant.
 * @param pictureId The ID of the restaurant's picture.
 * @param name The name of the restaurant.
 * @param rating The rating of the restaurant.
 * @param description The description of the restaurant.
 * @param id The ID of the restaurant.
 */
data class Restaurant(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("pictureId")
	val pictureId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String
)

/**
 * Data class representing a customer review.
 * @param date The date of the review.
 * @param review The content of the review.
 * @param name The name of the reviewer.
 */
data class CustomerReviewsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("name")
	val name: String
)

/**
 * Data class representing the response after posting a review to the restaurant.
 * It contains a list of customer reviews, an error status, and an optional message.
 *
 * @property customerReviews The list of customer reviews.
 * @property error A boolean indicating whether an error occurred.
 * @property message An optional message providing additional information.
 */
data class PostReviewResponse(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)