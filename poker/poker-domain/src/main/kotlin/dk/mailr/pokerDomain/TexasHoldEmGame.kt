package dk.mailr.pokerDomain

import dk.mailr.buildingblocks.domain.DomainEntity
import dk.mailr.buildingblocks.domain.EntityId
import kotlinx.serialization.Serializable

@Serializable
data class TexasHoldEmGame(
    override val id: EntityId<TexasHoldEmGame>,
) : DomainEntity<TexasHoldEmGame>()
