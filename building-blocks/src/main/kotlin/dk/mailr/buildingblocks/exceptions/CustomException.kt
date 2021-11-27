package dk.mailr.buildingblocks.exceptions

sealed class CustomException : Exception()

class NotFoundException(override val message: String) : CustomException()
