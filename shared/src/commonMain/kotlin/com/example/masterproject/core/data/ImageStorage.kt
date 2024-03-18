package com.example.masterproject.core.data
/**
 * Note: If images want to be save async then i have to get the permission and look out for the android api version
 * Common interface for image storage operations.
 * This allows saving, retrieving, and deleting images in a platform-specific storage location.
 *
 * Same Problem here as in LocationProvider. The implementation is very platform specific and so it does seem that there is
 * a good solution into storing the capured images. In this case tho, its only true for the iOS part.
 * Android worked here as a charm.
 */
expect class ImageStorage {
    /**
     * Saves an image byte array and returns the file name it was saved as.
     *
     * @param bytes The image byte array to save
     * @return The file name the image was saved as
     */
    suspend fun saveImage(bytes: ByteArray): String
    /**
     * Gets an image byte array for the given file name.
     *
     * @param fileName The name of the saved image file
     * @return The image byte array, or null if not found
     */
    suspend fun getImage(fileName: String): ByteArray?
    /**
     * Deletes the image with the given file name.
     *
     * @param fileName The name of the saved image file to delete
     */
    suspend fun deleteImage(fileName: String)
}