package meteor.web.repository

import meteor.web.model.TransportLink
import org.springframework.data.jpa.repository.JpaRepository

interface TransportLinkRepository : JpaRepository<TransportLink, Long>
