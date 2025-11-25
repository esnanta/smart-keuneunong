package com.smart.keuneunong.data.network.model

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

@JacksonXmlRootElement(localName = "data")
data class WeatherData(
    @field:JacksonXmlProperty(localName = "forecast")
    var forecast: Forecast? = null
)

data class Forecast(
    @field:JacksonXmlProperty(localName = "domain", isAttribute = true)
    var domain: String? = null,

    @field:JacksonXmlElementWrapper(useWrapping = false)
    @field:JacksonXmlProperty(localName = "area")
    var areas: List<Area>? = null
)

data class Area(
    @field:JacksonXmlProperty(localName = "id", isAttribute = true)
    var id: String? = null,
    @field:JacksonXmlProperty(localName = "latitude", isAttribute = true)
    var latitude: String? = null,
    @field:JacksonXmlProperty(localName = "longitude", isAttribute = true)
    var longitude: String? = null,
    @field:JacksonXmlProperty(localName = "type", isAttribute = true)
    var type: String? = null,
    @field:JacksonXmlProperty(localName = "region", isAttribute = true)
    var region: String? = null,
    @field:JacksonXmlProperty(localName = "level", isAttribute = true)
    var level: String? = null,
    @field:JacksonXmlProperty(localName = "description", isAttribute = true)
    var description: String? = null,
    @field:JacksonXmlProperty(localName = "domain", isAttribute = true)
    var domain: String? = null,
    @field:JacksonXmlProperty(localName = "tags", isAttribute = true)
    var tags: String? = null,

    @field:JacksonXmlElementWrapper(useWrapping = false)
    @field:JacksonXmlProperty(localName = "parameter")
    var parameters: List<Parameter>? = null
)

data class Parameter(
    @field:JacksonXmlProperty(localName = "id", isAttribute = true)
    var id: String? = null,
    @field:JacksonXmlProperty(localName = "description", isAttribute = true)
    var description: String? = null,
    @field:JacksonXmlProperty(localName = "type", isAttribute = true)
    var type: String? = null,

    @field:JacksonXmlElementWrapper(useWrapping = false)
    @field:JacksonXmlProperty(localName = "timerange")
    var timeranges: List<Timerange>? = null
)

data class Timerange(
    @field:JacksonXmlProperty(localName = "type", isAttribute = true)
    var type: String? = null,
    @field:JacksonXmlProperty(localName = "h", isAttribute = true)
    var h: String? = null,
    @field:JacksonXmlProperty(localName = "datetime", isAttribute = true)
    var datetime: String? = null,

    @field:JacksonXmlProperty(localName = "value")
    var value: Value? = null
)

data class Value(
    @field:JacksonXmlProperty(localName = "unit", isAttribute = true)
    var unit: String? = null,
    @field:JacksonXmlText
    var text: String? = null
)

