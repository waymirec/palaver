package net.waymire.palaver.authorization

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "AUTHN_IDENTITY")
data class AuthenticationIdentityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String,
    val username: String,
    val email: String,
    val passwordHash: String,
    val passwordVsn: String,
    @ManyToMany
    @JoinTable(
        name = "IDENTITY_ROLE",
        joinColumns = [JoinColumn(name = "identity_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: List<RoleEntity>
)

@Entity
@Table(name = "ROLE")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String,
    val name: String,
    val parentId: String,
    @ManyToMany
    @JoinTable(
        name = "ROLE_AUTHORIZATION",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "authorization_id")]
    )
    val authorizations: List<AuthorizationEntity>
)

@Entity
@Table(name = "CAPABILITY")
data class CapabilityEntity(
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    val id: String,
    val name: String
)

@Entity
@Table(name = "RESOURCE")
data class ResourceEntity(
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    val id: String,
    val name: String,
    @OneToMany
    val authorizations: List<AuthorizationEntity>
)

@Entity
@Table(name = "AUTHORIZATION")
data class AuthorizationEntity(
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    val id: String,
    val name: String,
    val resourceId:
    String, val capabilityId: String
)

