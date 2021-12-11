package dk.mailr.pokerApplication

import dk.mailr.buildingblocks.fakes.FakeRepository
import dk.mailr.pokerDomain.TexasHoldEmGame

class FakeGameRepository : GameRepository, FakeRepository<TexasHoldEmGame>()
