package meteor.web.service

import meteor.web.model.TileFlag
//import meteor.web.repository.RegionRepository
import org.springframework.stereotype.Service

@Service
class RegionService(
//    val regionRepository: RegionRepository
) {
    fun saveAll(tileFlags: List<TileFlag>) {
//        regionRepository.saveAll(tileFlags)
    }
}
