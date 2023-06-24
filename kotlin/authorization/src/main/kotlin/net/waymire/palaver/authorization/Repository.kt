package net.waymire.palaver.authorization

import org.springframework.data.jpa.repository.JpaRepository

interface AuthenticationIdentityRepository : JpaRepository<AuthenticationIdentityEntity, String> {
    fun findByUsername(username: String) : AuthenticationIdentityEntity
    fun findByEmail(email: String) : AuthenticationIdentityEntity
}

interface RoleRepository : JpaRepository<RoleEntity, String> {
    fun findByName(name: String) : RoleEntity
    fun findByParentId(parentId: String) : List<RoleEntity>
}

interface CapabilityRepository : JpaRepository<CapabilityEntity, String> {
    fun findByName(name: String) : CapabilityEntity
}

interface ResourceRepository : JpaRepository<ResourceEntity, String> {
    fun findByName(name: String) : ResourceEntity
}

interface AuthorizationRepository : JpaRepository<AuthorizationEntity, String> {
    fun findByName(name: String) : AuthorizationEntity
    fun findByResourceId(resourceId: String) : List<AuthorizationEntity>
    fun findByCapabilityId(capabilityId: String) : List<AuthorizationEntity>
}