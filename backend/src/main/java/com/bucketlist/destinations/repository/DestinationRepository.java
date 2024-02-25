package com.bucketlist.destinations.repository;

import com.bucketlist.destinations.model.Destination;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
     List<Destination> findDestinationsByIsPublic(boolean isPublic, Pageable pageable);

     List<Destination> findDestinationByIsPublicAndDestinationCityContainingIgnoreCase(boolean isPublic, String destinationCity, Pageable pageable);

     List<Destination> findDestinationByIsPublicAndDestinationNameContainingIgnoreCase(boolean isPublic, String destinationName, Pageable pageable);

     List<Destination> findDestinationByIsPublicAndDestinationCountryContainingIgnoreCase(boolean isPublic, String destinationCountry, Pageable pageable);

     @Query(value = "SELECT d.destination_id, d.destination_country, d.destination_city, d.is_public, bl.description, d.destination_name\n" +
             "FROM \"Destination\" d\n" +
             "INNER JOIN \"BucketList\" bl ON d.destination_id = bl.destination_id\n" +
             "WHERE bl.user_id = %?1%\n" +
             "AND (d.destination_country ILIKE %?2% \n" +
             "AND d.destination_city ILIKE %?3%\n" +
             "AND d.destination_name ILIKE %?4%)\n" +
             "ORDER BY bl.destination_in_list_id", nativeQuery = true)
     List<Destination> findDestinationsForGivenUserId(Long userId, String country, String city, String name, Pageable pageable);

     Destination findDestinationByDestinationNameAndDestinationCityAndDestinationCountry(String destinationName, String destinationCity, String destinationCountry);
}
