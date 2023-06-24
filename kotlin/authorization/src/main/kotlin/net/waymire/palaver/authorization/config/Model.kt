package net.waymire.palaver.authorization.config

data class Role(val id: String, val name: String, val parentId: String)
data class Capability(val id: String, val name: String)
data class Resource(val id: String, val name: String)
data class Authorization(val id: String, val name: String, val resourceId: String, val capabilityId: String)
