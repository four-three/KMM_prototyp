package com.example.masterproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform