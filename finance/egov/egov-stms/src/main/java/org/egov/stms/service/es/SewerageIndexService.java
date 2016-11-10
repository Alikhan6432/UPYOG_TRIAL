/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) 2016  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *          1) All versions of this program, verbatim or modified must carry this
 *             Legal Notice.
 *
 *          2) Any misrepresentation of the origin of the material is prohibited. It
 *             is required that all modified versions of this material be marked in
 *             reasonable ways as different from the original version.
 *
 *          3) This license does not grant any rights to any user of the program
 *             with regards to rights under trademark law for use of the trade names
 *             or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.stms.service.es;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.egov.collection.entity.es.CollectionDocument;
import org.egov.infra.admin.master.entity.City;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.utils.DateUtils;
import org.egov.ptis.domain.model.AssessmentDetails;
import org.egov.ptis.domain.model.OwnerName;
import org.egov.stms.elasticSearch.entity.DailySTCollectionReportSearch;
import org.egov.stms.elasticSearch.entity.SewerageCollectFeeSearchRequest;
import org.egov.stms.elasticSearch.entity.SewerageConnSearchRequest;
import org.egov.stms.elasticSearch.entity.SewerageNoticeSearchRequest;
import org.egov.stms.entity.es.SewerageIndex;
import org.egov.stms.repository.es.SewerageIndexRepository;
import org.egov.stms.transactions.entity.SewerageApplicationDetails;
import org.egov.stms.utils.constants.SewerageTaxConstants;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SewerageIndexService {

    @Autowired
    private CityService cityService;

    @Autowired
    private SewerageIndexRepository sewerageIndexRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public SewerageIndex createSewarageIndex(final SewerageApplicationDetails sewerageApplicationDetails,
            final AssessmentDetails assessmentDetails) {
        final City cityWebsite = cityService.getCityByURL(ApplicationThreadLocals.getDomainName());

        final SewerageIndex sewarageIndex = new SewerageIndex();
        sewarageIndex.setUlbName(cityWebsite.getName());
        sewarageIndex.setApplicationCreatedBy(sewerageApplicationDetails.getCreatedBy().getName());
        sewarageIndex.setId(cityWebsite.getCode().concat("-").concat(sewerageApplicationDetails.getApplicationNumber()));
        sewarageIndex.setApplicationDate(sewerageApplicationDetails.getApplicationDate());
        sewarageIndex.setApplicationNumber(sewerageApplicationDetails.getApplicationNumber());
        sewarageIndex.setApplicationStatus(sewerageApplicationDetails.getStatus() != null
                ? sewerageApplicationDetails.getStatus().getDescription() : "");
        sewarageIndex.setConsumerNumber(sewerageApplicationDetails.getApplicationNumber());
        sewarageIndex.setApplicationType(sewerageApplicationDetails.getApplicationType() != null
                ? sewerageApplicationDetails.getApplicationType().getName() : "");
        sewarageIndex.setConnectionStatus(sewerageApplicationDetails.getConnection().getStatus() != null
                ? sewerageApplicationDetails.getConnection().getStatus().name() : "");
        sewarageIndex.setCreatedDate(sewerageApplicationDetails.getCreatedDate());
        sewarageIndex.setShscNumber(sewerageApplicationDetails.getConnection().getShscNumber() != null
                ? sewerageApplicationDetails.getConnection().getShscNumber() : "");
        sewarageIndex.setDisposalDate(sewerageApplicationDetails.getDisposalDate());

        sewarageIndex
                .setExecutionDate(sewerageApplicationDetails.getConnection().getExecutionDate());
        sewarageIndex.setIslegacy(sewerageApplicationDetails.getConnection().getLegacy());
        sewarageIndex.setNoOfClosets_nonResidential(
                sewerageApplicationDetails.getConnectionDetail().getNoOfClosetsNonResidential());
        sewarageIndex
                .setNoOfClosets_residential(sewerageApplicationDetails.getConnectionDetail().getNoOfClosetsResidential());
        sewarageIndex.setPropertyIdentifier(sewerageApplicationDetails.getConnectionDetail().getPropertyIdentifier() != null
                ? sewerageApplicationDetails.getConnectionDetail().getPropertyIdentifier() : "");
        sewarageIndex.setPropertyType(sewerageApplicationDetails.getConnectionDetail().getPropertyType() != null
                ? sewerageApplicationDetails.getConnectionDetail().getPropertyType().name() : "");
        if (sewerageApplicationDetails.getEstimationDate() != null)
            sewarageIndex.setEstimationDate(sewerageApplicationDetails.getEstimationDate());
        sewarageIndex
                .setEstimationNumber(sewerageApplicationDetails.getEstimationNumber() != null ? sewerageApplicationDetails
                        .getEstimationNumber() : "");
        if (sewerageApplicationDetails.getWorkOrderDate() != null)
            sewarageIndex.setWorkOrderDate(sewerageApplicationDetails.getWorkOrderDate());
        sewarageIndex
                .setWorkOrderNumber(sewerageApplicationDetails.getWorkOrderNumber() != null ? sewerageApplicationDetails
                        .getWorkOrderNumber() : "");
        if (sewerageApplicationDetails.getClosureNoticeDate() != null)
            sewarageIndex
                    .setClosureNoticeDate(sewerageApplicationDetails.getClosureNoticeDate());
        sewarageIndex
                .setClosureNoticeNumber(
                        sewerageApplicationDetails.getClosureNoticeNumber() != null ? sewerageApplicationDetails
                                .getClosureNoticeNumber() : "");
        Iterator<OwnerName> ownerNameItr = null;
        if (null != assessmentDetails.getOwnerNames())
            ownerNameItr = assessmentDetails.getOwnerNames().iterator();
        final StringBuilder consumerName = new StringBuilder();
        final StringBuilder mobileNumber = new StringBuilder();
        if (null != ownerNameItr && ownerNameItr.hasNext()) {
            final OwnerName primaryOwner = ownerNameItr.next();
            consumerName.append(primaryOwner.getOwnerName() != null ? primaryOwner.getOwnerName() : "");
            mobileNumber.append(primaryOwner.getMobileNumber() != null ? primaryOwner.getMobileNumber() : "");
            while (ownerNameItr.hasNext()) {
                final OwnerName secondaryOwner = ownerNameItr.next();
                consumerName.append(",").append(secondaryOwner.getOwnerName() != null ? secondaryOwner.getOwnerName() : "");
                mobileNumber.append(",")
                        .append(secondaryOwner.getMobileNumber() != null ? secondaryOwner.getMobileNumber() : "");
            }
        }
        sewarageIndex.setMobileNumber(mobileNumber.toString());
        sewarageIndex.setConsumerName(consumerName.toString());
        sewarageIndex.setDoorNo(assessmentDetails.getHouseNo() != null ? assessmentDetails.getHouseNo() : "");
        sewarageIndex.setWard(
                assessmentDetails.getBoundaryDetails() != null ? assessmentDetails.getBoundaryDetails().getWardName() : "");
        sewarageIndex
                .setAddress(assessmentDetails.getPropertyAddress() != null ? assessmentDetails.getPropertyAddress() : "");
        // Setting application status is active or in-active
        sewarageIndex.setActive(sewerageApplicationDetails.isActive());
        sewerageIndexRepository.save(sewarageIndex);
        return sewarageIndex;
    }

    // TODO: CHECK LIKE CASES WORKING OR NOT IN CASE OF SEARCH BY CONSUMER NAME
    public BoolQueryBuilder getQueryFilter(final SewerageConnSearchRequest searchRequest) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(QueryBuilders.matchQuery("active", true));
        if (StringUtils.isNotBlank(searchRequest.getConsumerNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("consumerNumber", searchRequest.getConsumerNumber()));
        if (StringUtils.isNotBlank(searchRequest.getShscNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("shscNumber", searchRequest.getShscNumber()));
        if (StringUtils.isNotBlank(searchRequest.getApplicantName()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("consumerName", searchRequest.getApplicantName()));
        if (StringUtils.isNotBlank(searchRequest.getMobileNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("mobileNumber", searchRequest.getMobileNumber()));
        if (StringUtils.isNotBlank(searchRequest.getRevenueWard()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("ward", searchRequest.getRevenueWard()));
        if (StringUtils.isNotBlank(searchRequest.getDoorNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("doorNo", searchRequest.getDoorNumber()));
        return boolQuery;
    }

    public List<SewerageIndex> getSearchResultByBoolQuery(final BoolQueryBuilder boolQuery, final FieldSortBuilder sort) {
        List<SewerageIndex> resultList = new ArrayList<>();
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("sewerage").withQuery(boolQuery)
                .withSort(sort).build();
        resultList = elasticsearchTemplate.queryForList(searchQuery, SewerageIndex.class);
        return resultList;
    }

    public BoolQueryBuilder getSearchQueryFilter(final SewerageCollectFeeSearchRequest searchRequest) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("ulbName", searchRequest.getUlbName()));
        if (StringUtils.isNotBlank(searchRequest.getConsumerNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("consumerNumber", searchRequest.getConsumerNumber()));
        if (StringUtils.isNotBlank(searchRequest.getShscNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("shscNumber", searchRequest.getShscNumber()));
        if (StringUtils.isNotBlank(searchRequest.getApplicantName()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("consumerName", searchRequest.getApplicantName()));
        if (StringUtils.isNotBlank(searchRequest.getMobileNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("mobileNumber", searchRequest.getMobileNumber()));
        if (StringUtils.isNotBlank(searchRequest.getRevenueWard()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("ward", searchRequest.getRevenueWard()));
        if (StringUtils.isNotBlank(searchRequest.getDoorNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("doorNo", searchRequest.getDoorNumber()));
        return boolQuery;
    }

    public List<SewerageIndex> getCollectSearchResult(final BoolQueryBuilder boolQuery, final FieldSortBuilder sort) {
        List<SewerageIndex> resultList = new ArrayList<>();
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("sewerage").withQuery(boolQuery)
                .withSort(sort).build();

        resultList = elasticsearchTemplate.queryForList(searchQuery, SewerageIndex.class);
        return resultList;
    }

    public BoolQueryBuilder getDCRSearchResult(final DailySTCollectionReportSearch searchRequest) throws ParseException {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

        final Date fromDate = formatter.parse(searchRequest.getFromDate());
        final String formattedFromDate = newFormat.format(fromDate);

        final Date toDate = formatter.parse(searchRequest.getToDate());
        final String formattedToDate = newFormat.format(toDate);

        // setting toDate time to 23:59:59
        final DateTime dateTime = DateUtils.endOfGivenDate(new DateTime(formattedToDate));
        final Date formatToDate = dateTime.toDate();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("cityName", searchRequest.getUlbName()));
        if (StringUtils.isNotBlank(searchRequest.getFromDate()))
            boolQuery = boolQuery.filter(QueryBuilders.rangeQuery("receiptDate").gte(formattedFromDate).lte(formatToDate));
        if (StringUtils.isNotBlank(searchRequest.getCollectionMode()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("channel", searchRequest.getCollectionMode()));
        if (StringUtils.isNotBlank(searchRequest.getCollectionOperator()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("receiptCreator", searchRequest.getCollectionOperator()));
        if (StringUtils.isNotBlank(searchRequest.getStatus()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("status", searchRequest.getStatus()));
        return boolQuery;
    }

    public BoolQueryBuilder getDCRSewerageSearchResult(final DailySTCollectionReportSearch searchRequest) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("ulbName", searchRequest.getUlbName()));
        if (StringUtils.isNotBlank(searchRequest.getRevenueWard()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("ward", searchRequest.getRevenueWard()));
        return boolQuery;
    }

    public List<DailySTCollectionReportSearch> getDCRSewerageReportResult(final DailySTCollectionReportSearch searchRequest,
            final BoolQueryBuilder boolQuery,
            final FieldSortBuilder fieldSortBuilder) throws ParseException {
        List<CollectionDocument> collectionResultList = new ArrayList<>();
        final List<DailySTCollectionReportSearch> dcrCollectionList = new ArrayList<>();
        final List<DailySTCollectionReportSearch> resultList = new ArrayList<>();
        List<SewerageIndex> sewerageResultList = new ArrayList<>();
        DailySTCollectionReportSearch dcrReportObject;
        final SearchQuery receiptSearchQuery = new NativeSearchQueryBuilder().withIndices("receipts")
                .withQuery(boolQuery).withSort(new FieldSortBuilder("receiptDate").order(SortOrder.DESC)).build();
        collectionResultList = elasticsearchTemplate.queryForList(receiptSearchQuery, CollectionDocument.class);

        for (final CollectionDocument collectionObject : collectionResultList) {
            dcrReportObject = new DailySTCollectionReportSearch();
            dcrReportObject.setConsumerNumber(collectionObject.getConsumerCode());
            dcrReportObject.setReceiptNumber(collectionObject.getReceiptNumber());

            final SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            final SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            final Date receiptDate = dateFormat.parse(collectionObject.getReceiptDate().toString());
            dcrReportObject.setReceiptDate(myFormat.format(receiptDate));
            dcrReportObject.setPaidAt(collectionObject.getChannel());
            dcrReportObject.setPaymentMode(collectionObject.getPaymentMode());
            dcrReportObject.setStatus(collectionObject.getStatus());
            if (StringUtils.isNotBlank(collectionObject.getInstallmentFrom()))
                dcrReportObject.setFromDate(collectionObject.getInstallmentFrom());
            if (StringUtils.isNotBlank(collectionObject.getInstallmentTo()))
                dcrReportObject.setToDate(collectionObject.getInstallmentTo());
            dcrReportObject.setArrearAmount(collectionObject.getArrearAmount());
            dcrReportObject.setCurrentAmount(collectionObject.getCurrentAmount());
            dcrReportObject.setTotalAmount(collectionObject.getTotalAmount());
            dcrCollectionList.add(dcrReportObject);
        }

        final BoolQueryBuilder sewerageBoolQuery = getDCRSewerageSearchResult(searchRequest);
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("sewerage")
                .withQuery(sewerageBoolQuery).withSort(fieldSortBuilder).build();
        sewerageResultList = elasticsearchTemplate.queryForList(searchQuery, SewerageIndex.class);
        for (final SewerageIndex sewerageIndex : sewerageResultList)
            for (final DailySTCollectionReportSearch dcrReportObj : dcrCollectionList)
                if (dcrReportObj.getConsumerNumber().equals(sewerageIndex.getConsumerNumber())) {
                    dcrReportObj.setDoorNo(sewerageIndex.getDoorNo());
                    dcrReportObj.setShscNumber(sewerageIndex.getShscNumber());
                    dcrReportObj.setOwnerName(sewerageIndex.getConsumerName());
                    dcrReportObj.setRevenueWard(sewerageIndex.getWard());
                    resultList.add(dcrReportObj);
                }
        return resultList;
    }

    public BoolQueryBuilder getQueryFilterForNotice(final SewerageNoticeSearchRequest searchRequest) {
        BoolQueryBuilder boolQuery = null;
        if (searchRequest.getNoticeType() != null)
            if (searchRequest.getNoticeType().equals(SewerageTaxConstants.NOTICE_WORK_ORDER))
                boolQuery = QueryBuilders.boolQuery().filter(QueryBuilders.rangeQuery("workOrderDate")
                        .from(searchRequest.getNoticeGeneratedFrom())
                        .to(searchRequest.getNoticeGeneratedTo()));
            else if (searchRequest.getNoticeType().equals(SewerageTaxConstants.NOTICE_ESTIMATION))
                boolQuery = QueryBuilders.boolQuery().filter(QueryBuilders.rangeQuery("estimationDate")
                        .from(searchRequest.getNoticeGeneratedFrom())
                        .to(searchRequest.getNoticeGeneratedTo()));
            else if (searchRequest.getNoticeType().equals(SewerageTaxConstants.NOTICE_CLOSE_CONNECTION))
                boolQuery = QueryBuilders.boolQuery().filter(QueryBuilders.rangeQuery("closureNoticeDate")
                        .from(searchRequest.getNoticeGeneratedFrom())
                        .to(searchRequest.getNoticeGeneratedTo()));
        if (StringUtils.isNotBlank(searchRequest.getUlbName()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("ulbName", searchRequest.getUlbName()));
        if (StringUtils.isNotBlank(searchRequest.getShscNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("shscNumber", searchRequest.getShscNumber()));
        if (StringUtils.isNotBlank(searchRequest.getApplicantName()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("consumerName", searchRequest.getApplicantName()));
        if (StringUtils.isNotBlank(searchRequest.getMobileNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("mobileNumber", searchRequest.getMobileNumber()));
        if (StringUtils.isNotBlank(searchRequest.getRevenueWard()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("revenueWard", searchRequest.getRevenueWard()));
        if (StringUtils.isNotBlank(searchRequest.getDoorNumber()))
            boolQuery = boolQuery.filter(QueryBuilders.matchQuery("doorNumber", searchRequest.getDoorNumber()));
        return boolQuery;
    }

    public List<SewerageIndex> getNoticeSearchResultByBoolQuery(final BoolQueryBuilder boolQuery) {
        List<SewerageIndex> resultList = new ArrayList<>();
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("sewerage").withQuery(boolQuery)
                .withSort(new FieldSortBuilder("consumerName").order(SortOrder.DESC)).build();
        resultList = elasticsearchTemplate.queryForList(searchQuery, SewerageIndex.class);
        return resultList;
    }
}