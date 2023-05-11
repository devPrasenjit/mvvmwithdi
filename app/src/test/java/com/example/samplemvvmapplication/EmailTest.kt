package com.example.samplemvvmapplication

import com.example.samplemvvmapplication.utils.CommonFunctions
import org.junit.Assert
import org.junit.Test

class EmailTest {

    @Test
    fun testEmail(){
            val sut = CommonFunctions()
            val result = sut.isValidEmail("abc@mail")
            Assert.assertEquals("Valid email required",result)
    }
}