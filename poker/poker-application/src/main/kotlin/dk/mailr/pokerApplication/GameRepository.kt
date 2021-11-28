package dk.mailr.pokerApplication

import dk.mailr.buildingblocks.dataAccess.EntityRepository
import dk.mailr.pokerDomain.TexasHoldEmGame

interface GameRepository : EntityRepository<TexasHoldEmGame>
