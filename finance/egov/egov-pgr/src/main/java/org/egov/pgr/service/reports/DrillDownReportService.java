/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2015>  eGovernments Foundation

    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .

    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:

        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.

        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.

        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.

  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.pgr.service.reports;

import java.util.Date;

import org.egov.infstr.utils.HibernateUtil;
import org.hibernate.SQLQuery;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DrillDownReportService {
    private static final Logger LOG = LoggerFactory.getLogger(DrillDownReportService.class);
    String COMPLAINTSTATUS_COMPLETED = "Completed";

    public SQLQuery getDrillDownReportQuery(final DateTime fromDate, final DateTime toDate,
            final String complaintDateType, final String groupBy, final String department, final String boundary,
            final String complainttype, final String selecteduser) {

        final StringBuffer query = new StringBuffer();

        if (boundary != null && !"".equals(boundary)) {
            if (department != null && !"".equals(department)) {

                if (complainttype != null && !"".equals(complainttype))
                    query.append("  SELECT   emp.name||'~'|| pos.name    as name, "); // Next
                                                                                      // is
                                                                                      // userwise.
                else
                    query.append(" SELECT ctype.name as name, "); // mean user
                                                                  // selected
                                                                  // boundary
                                                                  // and
                                                                  // department.
                                                                  // Next is
                                                                  // complaint
                                                                  // type.
            } else
                query.append(" SELECT dept.name as name, "); // Mean get
                                                             // department list
                                                             // .
        } else if (department != null && !"".equals(department)) {
            if (complainttype != null && !"".equals(complainttype))
                query.append("  SELECT   emp.name||'~'|| pos.name    as name, ");
            else
                query.append(" SELECT ctype.name as name, ");
        } else if (complainttype != null && !"".equals(complainttype))
            query.append(" SELECT ctype.name as name, ");
        else if (selecteduser != null && !"".equals(selecteduser))
            query.append("  SELECT   emp.name||'~'|| pos.name    as name, ");
        else if (groupBy != null && !"".equals(groupBy) && groupBy.equalsIgnoreCase("ByBoundary"))
            query.append("SELECT bndryparent.name as name, ");
        else
            query.append("SELECT dept.name as name, ");

        query.append("   COUNT(CASE WHEN cs.name IN ('REGISTERED') THEN 1 END) registered , "
                + " COUNT(CASE WHEN cs.name IN ('FORWARDED','PROCESSING','REOPENED','NOTCOMPLETED') THEN 1 END) inprocess, "
                + " COUNT(CASE WHEN cs.name IN ('COMPLETED','WITHDRAWN','CLOSED') THEN 1 END) Completed, "
                + " COUNT(CASE WHEN cs.name IN ('REJECTED') THEN 1 END) Rejected ");

        query.append("  FROM egpgr_complaintstatus cs ,egpgr_complainttype ctype ,egpgr_complaint cd  left JOIN eg_boundary bndry on cd.location =bndry.id left JOIN eg_boundary bndryparent on  bndry.parent=bndryparent.id  left JOIN eg_department dept on cd.department =dept.id left join eg_position pos on cd.assignee=pos.id  left join view_egeis_employee emp on pos.id=emp.position ");

        buildWhereClause(fromDate, toDate, complaintDateType, query, department, boundary, complainttype, selecteduser);

        buildGroupByClause(groupBy, department, boundary, complainttype, selecteduser, query);

        return setParameterForDrillDownReportQuery(query.toString(), fromDate, toDate, complaintDateType);
    }

    private void buildGroupByClause(final String groupBy, final String department, final String boundary,
            final String complainttype, final String selecteduser, final StringBuffer query) {
        if (boundary != null && !"".equals(boundary)) {
            if (department != null && !"".equals(department)) {
                if (complainttype != null && !"".equals(complainttype))
                    query.append("  group by emp.name||'~'|| pos.name ");
                else

                    query.append("  group by ctype.name ");

            } else
                query.append("  group by dept.name ");
        } else if (department != null && !"".equals(department)) {

            if (complainttype != null && !"".equals(complainttype))
                query.append("  group by emp.name||'~'|| pos.name ");
            else
                query.append("  group by ctype.name ");
        } else if (complainttype != null && !"".equals(complainttype))
            query.append(" group by ctype.name  ");
        else if (groupBy != null && !"".equals(groupBy) && groupBy.equalsIgnoreCase("ByBoundary"))
            query.append("  group by bndryparent.name ");
        else
            query.append("  group by dept.name ");
    }

    private void buildWhereClause(final DateTime fromDate, final DateTime toDate, final String complaintDateType,
            final StringBuffer query, final String department, final String boundary, final String complainttype,
            final String selecteduser) {

        query.append(" WHERE cd.status  = cs.id and cd.complainttype= ctype.id  ");

        if (complaintDateType != null && complaintDateType.equals("lastsevendays"))
            query.append(" and cd.createddate >=   :fromDates ");
        else if (complaintDateType != null && complaintDateType.equals("lastthirtydays"))
            query.append(" and cd.createddate >=   :fromDates ");
        else if (complaintDateType != null && complaintDateType.equals("lastninetydays"))
            query.append(" and cd.createddate >=   :fromDates ");
        else if (fromDate != null && toDate != null)
            query.append(" and ( cd.createddate BETWEEN :fromDates and :toDates) ");
        else if (fromDate != null)
            query.append(" and cd.createddate >=   :fromDates ");
        else if (toDate != null)
            query.append(" and cd.createddate <=  :toDates ");

        if (boundary != null && !"".equals(boundary))
            if (boundary.equalsIgnoreCase("NOT AVAILABLE"))
                query.append(" and  bndryparent.name is null ");
            else {
                query.append(" and upper(trim(bndryparent.name))= '");
                query.append(boundary.toUpperCase()).append("' ");
            }
        if (department != null && !"".equals(department))
            if (department.equalsIgnoreCase("NOT AVAILABLE"))
                query.append(" and  dept.name is null ");
            else {
                query.append(" and upper(trim(dept.name))=  '");
                query.append(department.toUpperCase()).append("' ");
            }
        if (complainttype != null && !"".equals(complainttype)) {
            query.append(" and upper(trim(ctype.name))= '");
            query.append(complainttype.toUpperCase()).append("' ");
        }

    }

    private SQLQuery setParameterForDrillDownReportQuery(final String querykey, final DateTime fromDate,
            final DateTime toDate, final String complaintDateType) {
        final SQLQuery qry = HibernateUtil.getCurrentSession().createSQLQuery(querykey);

        if (complaintDateType != null && complaintDateType.equals("lastsevendays"))
            qry.setParameter("fromDates", getCurrentDateWithOutTime().minusDays(7).toDate());
        else if (complaintDateType != null && complaintDateType.equals("lastthirtydays"))
            qry.setParameter("fromDates", getCurrentDateWithOutTime().minusDays(30).toDate());
        else if (complaintDateType != null && complaintDateType.equals("lastninetydays"))
            qry.setParameter("fromDates", getCurrentDateWithOutTime().minusDays(90).toDate());
        else if (fromDate != null && toDate != null) {
            qry.setParameter("fromDates", resetTimeByPassingDate(fromDate));
            qry.setParameter("toDates", getEndOfDayByDate(toDate));

        } else if (fromDate != null)
            qry.setParameter("fromDates", resetTimeByPassingDate(fromDate));
        else if (toDate != null)
            qry.setParameter("toDates", getEndOfDayByDate(toDate));
        return qry;

    }

    private Date getEndOfDayByDate(final DateTime fromDate) {
        return fromDate.withTime(23, 59, 59, 999).toDate();
    }

    private Date resetTimeByPassingDate(final DateTime fromDate) {
        return fromDate.withTime(0, 0, 0, 0).toDate();
    }

    private DateTime getCurrentDateWithOutTime() {
        return new LocalDateTime().withTime(0, 0, 0, 0).toDateTime();
    }

    public SQLQuery getDrillDownReportQuery(final DateTime fromDate, final DateTime toDate,
            final String complaintDateType, final String department, final String boundary, final String complainttype,
            final String selecteduser) {
        final StringBuffer query = new StringBuffer();

        query.append(" SELECT  distinct complainant.id as complaintid, crn,cd.createddate,complainant.name as complaintname,cd.details,cs.name as status , bndry.name as boundaryname FROM egpgr_complaintstatus cs ,egpgr_complainttype ctype ,egpgr_complaint cd left JOIN eg_boundary bndry on cd.location =bndry.id left JOIN eg_boundary bndryparent on  bndry.parent=bndryparent.id  left JOIN eg_department dept on cd.department =dept.id  left join eg_position pos on cd.assignee=pos.id left join view_egeis_employee emp on pos.id=emp.position ,"
                + " egpgr_complainant complainant ");

        buildWhereClause(fromDate, toDate, complaintDateType, query, department, boundary, complainttype, selecteduser);
        query.append(" and complainant.id=cd.complainant   ");
        if (selecteduser != null && !"".equals(selecteduser)) {
            query.append(" and upper(emp.name)= '");
            query.append(selecteduser.toUpperCase()).append("' ");
        }

        return setParameterForDrillDownReportQuery(query.toString(), fromDate, toDate, complaintDateType);
    }

}
