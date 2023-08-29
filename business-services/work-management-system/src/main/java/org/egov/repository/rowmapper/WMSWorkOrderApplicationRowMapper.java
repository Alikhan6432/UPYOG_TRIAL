package org.egov.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.egov.web.models.ScheduleOfRateApplication;
import org.egov.web.models.WMSContractorApplication;
import org.egov.web.models.WMSWorkApplication;
import org.egov.web.models.WMSWorkOrderApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class WMSWorkOrderApplicationRowMapper implements ResultSetExtractor<List<WMSWorkOrderApplication>> {
    public List<WMSWorkOrderApplication> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer,WMSWorkOrderApplication> wmsWorkOrderApplicationMap = new LinkedHashMap<>();

        while (rs.next()){
            int workOrderId = rs.getInt("oWorkOrderId");
            WMSWorkOrderApplication wmsWorkOrderApplication = wmsWorkOrderApplicationMap.get(workOrderId);

            if(wmsWorkOrderApplication == null) {

                Date lastModifiedTime = rs.getDate("oWorkOrderDate");
                if (rs.wasNull()) {
                    lastModifiedTime = null;
                }
                wmsWorkOrderApplication = WMSWorkOrderApplication.builder()
                        .workOrderId(rs.getInt("oWorkOrderId"))
                        .workOrderDate(rs.getString("oWorkOrderDate"))
                        .agreementNo(rs.getInt("oAgreementNo"))
                        .contractorName(rs.getString("oContractorName"))
                        .workName(rs.getString("oWorkName"))
                        .contractValue(rs.getString("oContractValue"))
                        .stipulatedCompletionPeriod(rs.getString("oStipulatedCompletionPeriod"))
                        .tenderNumber(rs.getInt("oTenderNumber"))
                        .tenderDate(rs.getString("oTenderDate"))
                        .dateOfCommencement(rs.getString("oDateOfCommencement"))
                        .workAssignee(rs.getString("oWorkAssignee"))
                        .documentDescription(rs.getString("oDocumentDescription"))
                        .termsAndConditions(rs.getString("oTermsAndConditions"))
                        .build();
            }
            //addChildrenToProperty(rs, sorApplication);
            wmsWorkOrderApplicationMap.put(workOrderId, wmsWorkOrderApplication);
        }
        return new ArrayList<>(wmsWorkOrderApplicationMap.values());
    }

}
