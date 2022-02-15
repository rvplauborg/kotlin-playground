package dk.mailr.buildingblocks

import com.mongodb.client.ClientSession
import com.mongodb.client.MongoDatabase
import dk.mailr.buildingblocks.dataAccess.MongoEntityRepository
import dk.mailr.buildingblocks.domain.EntityId
import dk.mailr.buildingblocks.exceptions.NotFoundException
import dk.mailr.buildingblocks.fakes.FakeMediator
import dk.mailr.buildingblocks.fakes.TestEntity
import dk.mailr.buildingblocks.fakes.fixture
import dk.mailr.buildingblocks.mediator.DomainEventPublisher
import dk.mailr.buildingblocks.mediator.EventPublisher
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSingleton
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.litote.kmongo.getCollection
import java.util.UUID

class MongoEntityRepositoryTest : BaseEntityRepositoryTest() {
    internal class RepositoryForTest(
        mongoDatabase: MongoDatabase,
        eventsPublisher: EventPublisher,
        clientSession: ClientSession,
    ) : MongoEntityRepository<TestEntity>(eventsPublisher, clientSession) {
        override val mongoCollection = mongoDatabase.getCollection<TestEntity>()
    }

    private val mediator = FakeMediator()
    private val entityRepository = RepositoryForTest(mongoDatabase, DomainEventPublisher(mediator), mongoClient.startSession())

    @Test
    fun `should be possible to add an entity`() {
        val entityToSave = TestEntity(EntityId(UUID.randomUUID()))

        entityRepository.save(entityToSave)

        val entity = entityRepository.findById(entityToSave.id)
        entity.shouldNotBeNull()
    }

    @Test
    fun `should error when attempting to read entity which does not exist`() {
        val randomId = EntityId<TestEntity>(UUID.randomUUID())
        shouldThrow<NotFoundException> { entityRepository.getById(randomId) }
    }

    @Test
    fun `should return null when attempting to find entity which does not exist`() {
        val randomId = EntityId<TestEntity>(UUID.randomUUID())

        val entity = entityRepository.findById(randomId)

        entity.shouldBeNull()
    }

    @Test
    fun `should return entity when attempting to find entity which exists`() {
        val entityToSave = TestEntity(EntityId(UUID.randomUUID()))
        val savedEntity = entityRepository.save(entityToSave)

        val entity = entityRepository.findById(entityToSave.id)

        entity shouldBe savedEntity
    }

    @Test
    fun `should return empty list if no entities with given id when attempting to find by ids`() {
        val ids = fixture<List<EntityId<TestEntity>>>()

        val entities = entityRepository.findByIds(ids)

        entities.shouldBeEmpty()
    }

    @Test
    fun `should return matching entities when attempting to find by ids`() {
        val entitiesToSave = fixture<List<TestEntity>>()
        val idsForNonExistingEntities = fixture<List<EntityId<TestEntity>>>()
        entitiesToSave.forEach { entityRepository.save(it) }

        val idsToFindFor = entitiesToSave.map { it.id } + idsForNonExistingEntities
        val entities = entityRepository.findByIds(idsToFindFor)

        entities shouldContainAll entitiesToSave
    }

    @Test
    fun `should be possible to update an existing entity`() {
        val entityToSave = TestEntity(EntityId(UUID.randomUUID()))
        var savedEntity = entityRepository.save(entityToSave)

        savedEntity = entityRepository.save(savedEntity.withName("other-awesome-name"))

        val entity = entityRepository.getById(savedEntity.id)
        entity.name shouldBe "other-awesome-name"
    }

    @Test
    fun `should be possible to delete an existing entity`() {
        val entityToSave = TestEntity(EntityId(UUID.randomUUID()))
        val savedEntity = entityRepository.save(entityToSave)

        entityRepository.delete(savedEntity)

        shouldThrow<NotFoundException> { entityRepository.getById(entityToSave.id) }
    }

    @Test
    fun `should be possible to publish events queued from save by calling saveChanges`() = runBlocking<Unit> {
        val entityToSave = TestEntity(EntityId(UUID.randomUUID()))
        entityRepository.save(entityToSave.withEvent())

        entityRepository.saveChanges()

        mediator.notifications.shouldBeSingleton()
    }

    @Test
    fun `should be possible to publish events queued from delete by calling saveChanges`() = runBlocking<Unit> {
        val entityToSave = TestEntity(EntityId(UUID.randomUUID()))
        val savedEntity = entityRepository.save(entityToSave.withEvent())
        entityRepository.delete(savedEntity.withEvent())

        entityRepository.saveChanges()

        mediator.notifications shouldHaveSize 2
    }
}
