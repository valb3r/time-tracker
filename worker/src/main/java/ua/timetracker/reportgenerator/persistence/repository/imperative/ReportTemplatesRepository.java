package ua.timetracker.reportgenerator.persistence.repository.imperative;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.timetracker.shared.persistence.entity.report.ReportTemplate;

import static ua.timetracker.shared.persistence.entity.realationships.Relationships.HAS_CHILD;

@Repository
public interface ReportTemplatesRepository extends Neo4jRepository<ReportTemplate, Long> {

    @Query("MATCH (t:ReportTemplate)-[:" + HAS_CHILD + "]->(r:Report) WHERE id(r) = $reportId RETURN t")
    ReportTemplate getByReport(@Param("reportId") long reportId);
}
