package net.waymire.palaver.authorization

import org.springframework.stereotype.Service

@Service
class EntityService(
    private val authenticationIdentityRepository: AuthenticationIdentityRepository,
    private val roleRepository: RoleRepository,
    private val capabilityRepository: CapabilityRepository,
    private val resourceRepository: ResourceRepository,
    private val authorizationRepository: AuthorizationRepository
) {
    fun getAuthenticationIdentityById(id: String) = authenticationIdentityRepository.getReferenceById(id)
    fun findAuthenticationIdentityByUsername(username: String) = authenticationIdentityRepository.findByUsername(username)
    fun findAuthenticationIdentityByEmail(email: String) = authenticationIdentityRepository.findByEmail(email)

    fun getRoleById(id: String) = roleRepository.getReferenceById(id)
    fun findRoleByName(name: String) = roleRepository.findByName(name)
    fun saveRole(role: RoleEntity) = roleRepository.save(role)
    fun deleteRole(id: String) = roleRepository.deleteById(id)

    fun getCapabilityById(id: String) = capabilityRepository.getReferenceById(id)
    fun findCapabilityByName(name: String) = capabilityRepository.findByName(name)
    fun saveCapability(capability: CapabilityEntity) = capabilityRepository.save(capability)
    fun deleteCapability(id: String) = capabilityRepository.deleteById(id)

    fun getResourceById(id: String) = resourceRepository.getReferenceById(id)
    fun findResourceByName(name: String) = resourceRepository.findByName(name)
    fun saveResource(resource: ResourceEntity) = resourceRepository.save(resource)
    fun deleteResource(id: String) = resourceRepository.deleteById(id)

    fun getAuthorizationById(id: String) = authorizationRepository.getReferenceById(id)
    fun findAuthorizationByName(name: String) = authorizationRepository.findByName(name)
    fun saveAuthorization(authorization: AuthorizationEntity) = authorizationRepository.save(authorization)
    fun deleteAuthorization(id: String) = authorizationRepository.deleteById(id)
    fun findAuthorizationsByResource(id: String) = authorizationRepository.findByResourceId(id)

}


//@Service
//class RoleService(private val repository: RoleRepository) {
//    fun getById(id: String) = repository.getReferenceById(id)
//    fun findByName(name: String) = repository.findByName(name)
//    fun findAll() = repository.findAll()
//    fun save(entity: RoleEntity) = repository.save(entity)
//    fun deleteById(id: String) = repository.deleteById(id)
//}
//
//@Service
//class CapabilityService(private val repository: CapabilityRepository) {
//    fun getById(id: String) = repository.getReferenceById(id)
//    fun findByName(name: String) = repository.findByName(name)
//    fun findAll() = repository.findAll()
//    fun save(entity: CapabilityEntity) = repository.save(entity)
//    fun deleteById(id: String) = repository.deleteById(id)
//}
//
//@Service
//class ResourceService(private val repository: ResourceRepository) {
//    fun getById(id: String) = repository.getReferenceById(id)
//    fun findByName(name: String) = repository.findByName(name)
//    fun findAll() = repository.findAll()
//    fun save(entity: ResourceEntity) = repository.save(entity)
//    fun deleteById(id: String) = repository.deleteById(id)
//}
//
//@Service
//class AuthorizationService(private val repository: AuthorizationRepository) {
//    fun getById(id: String) = repository.getReferenceById(id)
//    fun findByName(name: String) = repository.findByName(name)
//    fun findAll() = repository.findAll()
//    fun save(entity: AuthorizationEntity) = repository.save(entity)
//    fun deleteById(id: String) = repository.deleteById(id)
//}