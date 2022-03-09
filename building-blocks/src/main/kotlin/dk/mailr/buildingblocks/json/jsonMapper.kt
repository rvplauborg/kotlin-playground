package dk.mailr.buildingblocks.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.litote.kmongo.id.jackson.IdJacksonModule

val jsonMapper: ObjectMapper = jacksonMapperBuilder()
    .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
    .build()
    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
