package ua.timetracker.reportgenerator.persistence.repository.imperative;

import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.timetracker.shared.persistence.entity.report.Report;
import ua.timetracker.shared.persistence.entity.report.ReportStatus;

import java.util.List;

@Repository
public interface ReportsRepository extends CrudRepository<Report, Long> {

    @Query("MATCH (r:Report) WHERE r.status = $status ORDER BY r.createdAt ASC LIMIT $maxToRead")
    List<Report> findAllByStatus(@Param("status") ReportStatus status, @Param("maxToRead") int maxToRead);
}
