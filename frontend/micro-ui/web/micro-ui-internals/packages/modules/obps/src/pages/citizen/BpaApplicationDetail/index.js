import { CardHeader, TextInput, Header, Toast, Card, StatusTable, Row, Loader, Menu, PDFSvg, SubmitBar, LinkButton, ActionBar, CheckBox, MultiLink, CardText, CardSubHeader, CardLabel } from "@upyog/digit-ui-react-components";
import React, { Fragment, useEffect, useState } from "react";
import { useParams, useHistory } from "react-router-dom";
import { useQueryClient } from "react-query";
import { useTranslation } from "react-i18next";
import BPAApplicationTimeline from "./BPAApplicationTimeline";
import DocumentDetails from "../../../components/DocumentDetails";
import ActionModal from "./Modal";
import OBPSDocument from "../../../pageComponents/OBPSDocuments";
import SubOccupancyTable from "../../../../../templates/ApplicationDetails/components/SubOccupancyTable";
import InspectionReport from "../../../../../templates/ApplicationDetails/components/InspectionReport";
import { getBusinessServices, getCheckBoxLabelData, getBPAFormData, convertDateToEpoch, printPdf, downloadPdf, getOrderDocuments  } from "../../../utils";
import cloneDeep from "lodash/cloneDeep";
import DocumentsPreview from "../../../../../templates/ApplicationDetails/components/DocumentsPreview";
import ScruntinyDetails from "../../../../../templates/ApplicationDetails/components/ScruntinyDetails";
import { Link } from "react-router-dom";
import CitizenConsent from "./CitizenConsent";

const BpaApplicationDetail = () => {
  const { id } = useParams();
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const stateCode = Digit.ULBService.getStateId();
  const isMobile = window.Digit.Utils.browser.isMobile();
  const queryClient = useQueryClient();
  const [showToast, setShowToast] = useState(null);
  const [isTocAccepted, setIsTocAccepted] = useState(false); 
  const [displayMenu, setDisplayMenu] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [showTermsModal, setShowTermsModal] = useState(false);
  const [selectedAction, setSelectedAction] = useState(null);
  const [showOptions, setShowOptions] = useState(false);
  const [checkBoxVisible, setCheckBoxVisible] = useState(false);
  const [isEnableLoader, setIsEnableLoader] = useState(false);
  const [viewTimeline, setViewTimeline]=useState(false);
  sessionStorage.removeItem("BPA_SUBMIT_APP");
  sessionStorage.setItem("isEDCRDisable", JSON.stringify(true));
  sessionStorage.setItem("BPA_IS_ALREADY_WENT_OFF_DETAILS", JSON.stringify(false));

  const user = Digit.UserService.getUser();
 
  const citizenmobilenumber = user?.info?.mobileNumber


  const history = useHistory();
  sessionStorage.setItem("bpaApplicationDetails", false);
  let isFromSendBack = false;
  const { data: stakeHolderDetails, isLoading: stakeHolderDetailsLoading } = Digit.Hooks.obps.useMDMS(stateCode, "StakeholderRegistraition", "TradeTypetoRoleMapping");
  const { isLoading: bpaDocsLoading, data: bpaDocs } = Digit.Hooks.obps.useMDMS(stateCode, "BPA", ["DocTypeMapping"]);
  const { data, isLoading } = Digit.Hooks.obps.useBPADetailsPage(tenantId, { applicationNo: id });
  console.log("datata",data);
  const { isMdmsLoading, data: mdmsData } = Digit.Hooks.obps.useMDMS(tenantId.split(".")[0], "BPA", ["RiskTypeComputation"]);
  const mutation = Digit.Hooks.obps.useObpsAPI(data?.applicationData?.tenantId, false);
  let workflowDetails = Digit.Hooks.useWorkflowDetails({
    tenantId: data?.applicationData?.tenantId,
    id: id,
    moduleCode: "OBPS",
    config: {
      enabled: !!data
    }
  });

  const [agree, setAgree] = useState(false);
  const setdeclarationhandler = () => {
    setAgree(!agree);
  };

  const [showTermsPopup, setShowTermsPopup] = useState(false);
  const [showMobileInput, setShowMobileInput] = useState(false);
  const [mobileNumber, setMobileNumber] = useState(citizenmobilenumber || "");
  const [showOTPInput, setShowOTPInput] = useState(false);
  const [otp, setOTP] = useState('');
  const [isOTPVerified, setIsOTPVerified] = useState(false);
  const [otpError, setOTPError] = useState("");
  const [otpVerifiedTimestamp, setOTPVerifiedTimestamp] = useState(null);
  

  const handleTermsLinkClick = () => {
    if (isOTPVerified){
      setShowTermsPopup(true);
    }
    else{
      alert("Please verify yourself")
    }
    
  }

  const checkLabels = () => {
    return (
      <div>
            {t("I_AGREE_TO_BELOW_UNDERTAKING")}
            <LinkButton
              label={t("DECLARATION_UNDER_SELF_CERTIFICATION_SCHEME")}
              onClick={handleTermsLinkClick}
            />
      </div>
    );
  };

  const handleVerifyClick = () => {
    setShowMobileInput(true);
  };

  const handleMobileNumberChange = (e) => {
    setMobileNumber(e.target.value);
  };

  const handleGetOTPClick = async () => {
    // Call the Digit.UserService.sendOtp API to send the OTP
    try {
      const response = await Digit.UserService.sendOtp({otp:{mobileNumber:mobileNumber, tenantId: user?.info?.tenantId, userType: user?.info?.type , type: "login"}});
      if (response.isSuccessful) {
        setShowOTPInput(true);
      } else {
        // Handle error case if OTP sending fails
        console.error("Error sending OTP Response is false:", response.error);
      }
    } catch (error) {
      console.error("Error sending OTP:", error);
    }
  };

 

  const handleOTPChange = (e) => {
    setOTP(e.target.value);
  };

  const requestData = {
    username:mobileNumber,
    password:otp,
    tenantId: user?.info?.tenantId,
    userType: user?.info?.type
  };

  const handleVerifyOTPClick = async () => {
    // Call the API to verify the OTP
    try {
      const response = await Digit.UserService.authenticate(requestData);
      if (response.ResponseInfo.status==="Access Token generated successfully") {
        setIsOTPVerified(true);
        setOTPError(t("VERIFIED"));
        setOTPVerifiedTimestamp(new Date());
      } else {
        setIsOTPVerified(false);
        setOTPError(t("WRONG OTP"));
      }
    } catch (error) {
      console.error("Error verifying OTP:", error);
      setIsOTPVerified(false);
      setOTPError(t("Error verifying OTP"));
    }
  };

  const isValidMobileNumber = mobileNumber.length === 10 && /^[0-9]+$/.test(mobileNumber);
  const citizenvalidations = sessionStorage.getItem("CitizenConsentdocFilestoreid") ? true : false ;


  let businessService = [];

  if(data && data?.applicationData?.businessService === "BPA_LOW")
  {
    businessService = ["BPA.LOW_RISK_PERMIT_FEE"]
  }
  else if(data && data?.applicationData?.businessService === "BPA" && data?.applicationData?.riskType === "HIGH")
  {
    businessService = ["BPA.NC_APP_FEE","BPA.NC_SAN_FEE"];
  }
  else
  {
    businessService = ["BPA.NC_OC_APP_FEE","BPA.NC_OC_SAN_FEE"];
  }

  useEffect(() => {
    if(!bpaDocsLoading && !isLoading){
      let filtredBpaDocs = [];
      if (bpaDocs?.BPA?.DocTypeMapping) {
        filtredBpaDocs = bpaDocs?.BPA?.DocTypeMapping?.filter(ob => (ob.WFState == "INPROGRESS" && ob.RiskType == data?.applicationData?.riskType && ob.ServiceType == data?.applicationData?.additionalDetails?.serviceType && ob.applicationType == data?.applicationData?.additionalDetails?.applicationType))
        let documents = data?.applicationDetails?.filter((ob) => ob.title === "BPA_DOCUMENT_DETAILS_LABEL")[0]?.additionalDetails?.obpsDocuments?.[0]?.values;
        let RealignedDocument = [];
        filtredBpaDocs && filtredBpaDocs?.[0]?.docTypes && filtredBpaDocs?.[0]?.docTypes.map((ob) => {
            documents && documents.filter(x => ob.code === x.documentType.slice(0,x.documentType.lastIndexOf("."))).map((doc) => {
                RealignedDocument.push(doc);
            })
        })
        const newApplicationDetails = data?.applicationDetails.map((obj) => {
          if(obj.title === "BPA_DOCUMENT_DETAILS_LABEL")
          {
            return {...obj, additionalDetails:{obpsDocuments:[{title:"",values:RealignedDocument}]}}
          }
          return obj;
        })
        
        data.applicationDetails = [...newApplicationDetails];
    }
    }
  },[bpaDocs,data])


  useEffect(() => {
    if (data?.applicationData?.status == "CITIZEN_APPROVAL_INPROCESS" || data?.applicationData?.status == "INPROGRESS") setCheckBoxVisible(true);
    else setCheckBoxVisible(false);
  },[data]);

  const getTranslatedValues = (dataValue, isNotTranslated) => {
    if(dataValue) {
      return !isNotTranslated ? t(dataValue) : dataValue
    } else {
      return t("NA")
    }
  };


  async function getRecieptSearch({tenantId, payments, ...params}) {
    let response=null;
    if (payments?.fileStoreId ) {
       response = { filestoreIds: [payments?.fileStoreId] };      
    }
    else{
      response = await Digit.PaymentService.generatePdf(tenantId, { Payments: [{...payments}] }, "bpa-receipt");
    }
    const fileStore = await Digit.PaymentService.printReciept(tenantId, { fileStoreIds: response.filestoreIds[0] });
    window.open(fileStore[response?.filestoreIds[0]], "_blank");
  }

  async function getPermitOccupancyOrderSearch({tenantId}, order, mode="download") {
    let currentDate = new Date();
    data.applicationData.additionalDetails.runDate = convertDateToEpoch(currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate());
    let requestData = {...data?.applicationData, edcrDetail:[{...data?.edcrDetails}]}
    const state = Digit.ULBService.getStateId();
  
    let count=0;

    for(let i=0;i<workflowDetails?.data?.processInstances?.length;i++){
      if((workflowDetails?.data?.processInstances[i]?.action==="POST_PAYMENT_APPLY" ||workflowDetails?.data?.processInstances[i]?.action==="PAY" ) && (workflowDetails?.data?.processInstances?.[i]?.state?.applicationStatus==="APPROVAL_INPROGRESS")   && count==0 ){
          requestData.additionalDetails.submissionDate=workflowDetails?.data?.processInstances[i]?.auditDetails?.createdTime;
          count=1;
        }
    }
    
    if(requestData?.additionalDetails?.approvedColony=="NO"){
      requestData.additionalDetails.permitData= "The plot has been officially regularized under No."+requestData?.additionalDetails?.NocNumber +"  dated dd/mm/yyyy, registered in the name of <name as per the NOC>. This regularization falls within the jurisdiction of "+ state +".Any form of misrepresentation of the NoC is strictly prohibited. Such misrepresentation renders the building plan null and void, and it will be regarded as an act of impersonation. Criminal proceedings will be initiated against the owner and concerned architect / engineer/ building designer / supervisor involved in such actions"
    }
    else if(requestData?.additionalDetails?.approvedColony=="YES" ){
      requestData.additionalDetails.permitData="The building plan falls under approved colony "+requestData?.additionalDetails?.nameofApprovedcolony
    }
    else{
      requestData.additionalDetails.permitData="The building plan falls under Lal Lakir"
    }
    let response = await Digit.PaymentService.generatePdf(tenantId, { Bpa: [requestData] }, order);
    const fileStore = await Digit.PaymentService.printReciept(tenantId, { fileStoreIds: response.filestoreIds[0] });
    window.open(fileStore[response?.filestoreIds[0]], "_blank");
    requestData["applicationType"] = data?.applicationData?.additionalDetails?.applicationType;
    let edcrResponse = await Digit.OBPSService.edcr_report_download({BPA: {...requestData}});
    const responseStatus = parseInt(edcrResponse.status, 10);
    if (responseStatus === 201 || responseStatus === 200) {
      mode == "print"
        ? printPdf(new Blob([edcrResponse.data], { type: "application/pdf" }))
        : downloadPdf(new Blob([edcrResponse.data], { type: "application/pdf" }), `edcrReport.pdf`);
    }
  }

  async function getRevocationPDFSearch({tenantId, ...params}) {
    let requestData = {...data?.applicationData}
    let response = await Digit.PaymentService.generatePdf(tenantId, { Bpa: [requestData] }, "bpa-revocation");
    const fileStore = await Digit.PaymentService.printReciept(tenantId, { fileStoreIds: response.filestoreIds[0] });
    window.open(fileStore[response?.filestoreIds[0]], "_blank");
  }

  useEffect(() => {
    const workflow = { action: selectedAction }
    switch (selectedAction) {
      case "APPROVE":
      case "SEND_TO_ARCHITECT":
      case "APPLY":
      case "SKIP_PAYMENT":
      case "POST_PAYMENT_APPROVE":
      case "POST_PAYMENT_APPLY":
        setShowModal(true);
    }
  }, [selectedAction]);

  const closeToast = () => {
    setShowToast(null);
  };

  const downloadDiagram = (val) => {
    location.href = val;
  }

  const handleChange = () => {

  }

  const closeModal = () => {
    setSelectedAction(null);
    setShowModal(false);
  };

  const closeTermsModal = () => {
    setShowTermsModal(false);
  }

  function onActionSelect(action) {
    let path = data?.applicationData?.businessService == "BPA_OC" ? "ocbpa" : "bpa";
    if(action === "FORWARD") {
      history.replace(`/digit-ui/citizen/obps/sendbacktocitizen/ocbpa/${data?.applicationData?.tenantId}/${data?.applicationData?.applicationNo}/check`, { data: data?.applicationData, edcrDetails: data?.edcrDetails });
    }
    if (action === "PAY") {
      window.location.assign(`${window.location.origin}/digit-ui/citizen/payment/collect/${`${getBusinessServices(data?.businessService, data?.applicationStatus)}/${id}/${data?.tenantId}?tenantId=${data?.tenantId}`}`);
    }
    if (action === "SEND_TO_CITIZEN"){
      if (workflowDetails?.data?.processInstances?.length > 2) {
        window.location.replace(`/digit-ui/citizen/obps/editApplication/${path}/${data?.applicationData?.tenantId}/${data?.applicationData?.applicationNo}`)
      } else {
        getBPAFormData(data?.applicationData, mdmsData, history, t)
      }
    }
    setSelectedAction(action);
    setDisplayMenu(false);
  }

  function checkForSubmitDisable () {
    if(checkBoxVisible) return isFromSendBack ? !isFromSendBack : !isTocAccepted;
    else return false;
  }

  

  // const submitAction = (workflow) => {
  //   setIsEnableLoader(true);
  //   mutation.mutate(
  //     { BPA: { ...data?.applicationData, workflow } },
  //     {
  //       onError: (error, variables) => {
  //         setIsEnableLoader(false);
  //         setShowModal(false);
  //         setShowToast({ key: "error", action: error?.response?.data?.Errors[0]?.message ? error?.response?.data?.Errors[0]?.message : error });
  //         setTimeout(closeToast, 5000);
  //       },
  //       onSuccess: (data, variables) => {
  //         setIsEnableLoader(false);
  //         history.replace(`/digit-ui/citizen/obps/response`, { data: data });
  //         setShowModal(false);
  //         setShowToast({ key: "success", action: selectedAction });
  //         setTimeout(closeToast, 5000);
  //         queryClient.invalidateQueries("BPA_DETAILS_PAGE");
  //         queryClient.invalidateQueries("workFlowDetails");
  //       },
  //     }
  //   );
  // }

  

  const submitAction = (workflow) => {
    setIsEnableLoader(true);
  
    // Check if "CITIZEN.UNDERTAKING" document already exists
    const citizenUndertakingExists = data?.applicationData?.documents?.some(
      (doc) => doc.documentType === "CITIZEN.UNDERTAKING"
    );
  
    // Create a new array with the existing documents and the new Citizenconsentform (if it doesn't exist)
    const updatedDocuments = [
      ...data?.applicationData?.documents.filter(
        (doc) => doc.documentType !== "CITIZEN.UNDERTAKING"
      ),
      ...(citizenUndertakingExists
        ? data?.applicationData?.documents.filter(
            (doc) => doc.documentType === "CITIZEN.UNDERTAKING"
          )
        : [
            {
              documentType: "CITIZEN.UNDERTAKING",
              fileStoreId: sessionStorage.getItem("CitizenConsentdocFilestoreid"),
              fileStore: sessionStorage.getItem("CitizenConsentdocFilestoreid"),
            },
          ]),
    ];
  
    // Update the applicationData object with the new documents array
    const updatedApplicationData = {
      ...data?.applicationData,
      documents: updatedDocuments,
    };
  
    mutation.mutate(
      { BPA: { ...updatedApplicationData, workflow } },
      {
        onError: (error, variables) => {
          setIsEnableLoader(false);
          setShowModal(false);
          setShowToast({
            key: "error",
            action: error?.response?.data?.Errors[0]?.message
              ? error?.response?.data?.Errors[0]?.message
              : error,
          });
          setTimeout(closeToast, 5000);
        },
        onSuccess: (data, variables) => {
          setIsEnableLoader(false);
          history.replace(`/digit-ui/citizen/obps/response`, { data: data });
          setShowModal(false);
          setShowToast({ key: "success", action: selectedAction });
          setTimeout(closeToast, 5000);
          queryClient.invalidateQueries("BPA_DETAILS_PAGE");
          queryClient.invalidateQueries("workFlowDetails");
        },
      }
    );
  };

  if (workflowDetails?.data?.nextActions?.length > 0 && data?.applicationData?.status == "CITIZEN_APPROVAL_INPROCESS") {
    const userInfo = Digit.UserService.getUser();
    const rolearray = userInfo?.info?.roles;
    if (data?.applicationData?.status == "CITIZEN_APPROVAL_INPROCESS") {
      if(rolearray?.length == 1 && rolearray?.[0]?.code == "CITIZEN") {
        workflowDetails.data.nextActions = workflowDetails?.data?.nextActions;
      } else {
        workflowDetails.data.nextActions = [];
      }
    } else if (data?.applicationData?.status == "INPROGRESS") {
      let isArchitect = false;
      stakeHolderDetails?.StakeholderRegistraition?.TradeTypetoRoleMapping?.map(type => {
        type?.role?.map(role => { roles.push(role); });
      });
      const uniqueRoles = roles.filter((item, i, ar) => ar.indexOf(item) === i);
      if (rolearray?.length > 1) {
        rolearray.forEach(role => {
          if (uniqueRoles.includes(role.code)) {
            isArchitect = true;
          }
        })
      }
      if (isArchitect) {
        workflowDetails.data.nextActions = workflowDetails?.data?.nextActions;
      } else {
        workflowDetails.data.nextActions = [];
      }
    }
  }


  if (workflowDetails?.data?.processInstances?.[0]?.action === "SEND_BACK_TO_CITIZEN") {
      if(isTocAccepted) setIsTocAccepted(true);
      isFromSendBack = true;
      const userInfo = Digit.UserService.getUser();
      const rolearray = userInfo?.info?.roles;
      if(rolearray?.length !== 1) {
        workflowDetails = {
          ...workflowDetails,
          data: {
            ...workflowDetails?.data,
            actionState: {
              nextActions: [],
            },
          },
          data: {
            ...workflowDetails?.data,
            nextActions: []
          }
        };
      }
  }

  if (isLoading || isEnableLoader) {
    return <Loader />
  }

  let dowloadOptions = [];

  if (data?.collectionBillDetails?.length > 0) {
    const bpaPayments = cloneDeep(data?.collectionBillDetails);
    bpaPayments.forEach(pay => {
      if (pay?.paymentDetails[0]?.businessService === "BPA.NC_OC_APP_FEE") {
        dowloadOptions.push({
          order: 1,
          label: t("BPA_APP_FEE_RECEIPT"),
          onClick: () => getRecieptSearch({ tenantId: data?.applicationData?.tenantId, payments: pay, consumerCodes: data?.applicationData?.applicationNo }),
        });
      }

      if (pay?.paymentDetails[0]?.businessService === "BPA.NC_OC_SAN_FEE") {
        dowloadOptions.push({
          order: 2,
          label: t("BPA_OC_DEV_PEN_RECEIPT"),
          onClick: () => getRecieptSearch({ tenantId: data?.applicationData?.tenantId, payments: pay, consumerCodes: data?.applicationData?.applicationNo }),
        });
      }

      if (pay?.paymentDetails[0]?.businessService === "BPA.LOW_RISK_PERMIT_FEE") {
        dowloadOptions.push({
          order: 1,
          label: t("BPA_FEE_RECEIPT"),
          onClick: () => getRecieptSearch({ tenantId: data?.applicationData?.tenantId, payments: pay, consumerCodes: data?.applicationData?.applicationNo }),
        });
      }

      if (pay?.paymentDetails[0]?.businessService === "BPA.NC_APP_FEE") {
        dowloadOptions.push({
          order: 1,
          label: t("BPA_APP_FEE_RECEIPT"),
          onClick: () => getRecieptSearch({ tenantId: data?.applicationData?.tenantId, payments: pay, consumerCodes: data?.applicationData?.applicationNo }),
        });
      }

      if (pay?.paymentDetails[0]?.businessService === "BPA.NC_SAN_FEE") {
        dowloadOptions.push({
          order: 2,
          label: t("BPA_SAN_FEE_RECEIPT"),
          onClick: () => getRecieptSearch({ tenantId: data?.applicationData?.tenantId, payments: pay, consumerCodes: data?.applicationData?.applicationNo }),
        });
      }
    })
  }


  if(data && data?.applicationData?.businessService === "BPA_LOW" && data?.collectionBillDetails?.length > 0) {
    !(data?.applicationData?.status.includes("REVOCATION")) && dowloadOptions.push({
      order: 3,
      label: t("BPA_PERMIT_ORDER"),
      onClick: () => getPermitOccupancyOrderSearch({tenantId: data?.applicationData?.tenantId},"buildingpermit-low"),
    });
    (data?.applicationData?.status.includes("REVOCATION")) && dowloadOptions.push({
      order: 3,
      label: t("BPA_REVOCATION_PDF_LABEL"),
      onClick: () => getRevocationPDFSearch({tenantId: data?.applicationData?.tenantId}),
    });
    
  } else if(data && data?.applicationData?.businessService === "BPA" && data?.collectionBillDetails?.length > 0) {
    if(data?.applicationData?.status==="APPROVED"){
    dowloadOptions.push({
      order: 3,
      label: t("BPA_PERMIT_ORDER"),
      onClick: () => getPermitOccupancyOrderSearch({tenantId: data?.applicationData?.tenantId},"buildingpermit"),
    });}
  } else {
    if(data?.applicationData?.status==="APPROVED"){
      dowloadOptions.push({
        order: 3,
        label: t("BPA_OC_CERTIFICATE"),
        onClick: () => getPermitOccupancyOrderSearch({tenantId: data?.applicationData?.tenantId},"occupancy-certificate"),
      });
    }
  }

  if(data?.comparisionReport){
    dowloadOptions.push({
      order: 4,
      label: t("BPA_COMPARISON_REPORT_LABEL"),
      onClick: () => window.open(data?.comparisionReport?.comparisonReport, "_blank"),
    });
  }

  dowloadOptions.sort(function (a, b) { return a.order - b.order; });

  if (workflowDetails?.data?.nextActions?.length > 0) {
    workflowDetails.data.nextActions = workflowDetails?.data?.nextActions?.filter(actn => actn.action !== "INITIATE");
    workflowDetails.data.nextActions = workflowDetails?.data?.nextActions?.filter(actn => actn.action !== "ADHOC");
    workflowDetails.data.nextActions = workflowDetails?.data?.nextActions?.filter(actn => actn.action !== "SKIP_PAYMENT");
  };

  if (data?.applicationDetails?.length > 0) {
    data.applicationDetails = data?.applicationDetails?.length > 0 && data?.applicationDetails?.filter(bpaData => Object.keys(bpaData).length !== 0);
  }


  const getCheckBoxLable = () => {
    return (
      <div>
        <span>{`${t("BPA_I_AGREE_THE_LABEL")} `}</span>
        <span style={{color: "#a82227", cursor: "pointer"}} onClick={() => setShowTermsModal(!showTermsModal)}>{t(`BPA_TERMS_AND_CONDITIONS_LABEL`)}</span>
      </div>
    )
  }
  const handleViewTimeline=()=>{ 
    const timelineSection=document.getElementById('timeline');
      if(timelineSection){
        timelineSection.scrollIntoView({behavior: 'smooth'});
      } 
      setViewTimeline(true);   
  };

  const results = data?.applicationDetails?.filter(element => {
    if (Object.keys(element).length !== 0) {
      return true;
    }
    return false;
  });

  if (results?.length > 0) {
    data.applicationDetails = results;
  }

 

  return (
    <Fragment>
      <div className="cardHeaderWithOptions" style={{ marginRight: "auto", maxWidth: "960px" }}>
        <Header styles={{fontSize: "32px", marginLeft: "10px"}}>{t("CS_TITLE_APPLICATION_DETAILS")}</Header>
        <div >
        {dowloadOptions && dowloadOptions.length > 0 && <MultiLink
          className="multilinkWrapper"
          onHeadClick={() => setShowOptions(!showOptions)}
          displayOptions={showOptions}
          options={dowloadOptions}
        />}
        <LinkButton label={t("VIEW_TIMELINE")} style={{ color:"#A52A2A"}} onClick={handleViewTimeline}></LinkButton>
        </div>
        
      </div>

     
      

      {data?.applicationDetails?.filter((ob) => Object.keys(ob).length > 0).map((detail, index, arr) => {
       

        return (
          <div>
            {!detail?.isNotAllowed ? <Card key={index} style={!detail?.additionalDetails?.fiReport && detail?.title === "" ? { marginTop: "-30px" } : {}}>

              {!detail?.isTitleVisible ? <CardSubHeader style={{fontSize: "24px"}}>{t(detail?.title)}</CardSubHeader> : null}
              
              <div style={detail?.isBackGroundColor ? { marginTop: "19px", background: "#FAFAFA", border: "1px solid #D6D5D4", borderRadius: "4px", padding: "8px", lineHeight: "19px", maxWidth: "950px", minWidth: "280px" } : {}}>

              <StatusTable>
                {/* to get common values */}
                {(detail?.isCommon && detail?.values?.length > 0) ? detail?.values?.map((value) => {
                  if (value?.isUnit) return <Row className="border-none" label={t(value?.title)} text={value?.value ? `${getTranslatedValues(value?.value, value?.isNotTranslated)} ${t(value?.isUnit)}` : t("CS_NA")} />
                  if (value?.isLink) return <Row className="border-none" label={t(value?.title)} text={<div><Link to={value?.to}><span className="link" style={{color: "#a82227"}}>{value?.value}</span></Link></div>} />
                  else return <Row className="border-none" label={t(value?.title)} text={getTranslatedValues(value?.value, value?.isNotTranslated) || t("CS_NA")} />
                }) : null}
                {/* to get additional common values */}
                {!detail?.isFeeDetails && detail?.additionalDetails?.values?.length > 0 ? detail?.additionalDetails?.values?.map((value) => (
                    <div>
                    {!detail?.isTitleRepeat && !value?.isHeader && !value?.isUnit ? <Row className="border-none" label={t(value?.title)} textStyle={value?.value === "Paid"?{color:"darkgreen"}:(value?.value === "Unpaid"?{color:"red"}:{})} text={value?.value ? getTranslatedValues(value?.value, value?.isNotTranslated) : t("CS_NA")} /> : null}
                    {!detail?.isTitleRepeat && value?.isUnit ? <Row className="border-none" label={t(value?.title)} text={value?.value ? `${getTranslatedValues(value?.value, value?.isNotTranslated)} ${t(value?.isUnit)}` : t("CS_NA")} /> : null}
                    {!detail?.isTitleRepeat && value?.isHeader ? <CardSubHeader style={{fontSize: "20px"}}>{t(value?.title)}</CardSubHeader> : null}
                    </div>
                )) : null}

                {/* to get subOccupancyValues values */}
                {(detail?.isSubOccupancyTable && detail?.additionalDetails?.subOccupancyTableDetails) ? <SubOccupancyTable edcrDetails={detail?.additionalDetails} applicationData={data?.applicationData} /> : null}

                {/* to get Scrutiny values */}
                {(detail?.isScrutinyDetails && detail?.additionalDetails?.scruntinyDetails?.length > 0) ?
                  detail?.additionalDetails?.scruntinyDetails.map((scrutiny) => (
                    <Fragment>
                      <Row className="border-none" label={t(scrutiny?.title)} />
                      <LinkButton
                        onClick={() => downloadDiagram(scrutiny?.value)}
                        label={<PDFSvg />}>
                      </LinkButton>
                      <p style={{ marginTop: "8px", marginBottom: "20px", fontWeight: "bold", fontSize: "16px", lineHeight: "19px", color: "#505A5F", fontWeight: "400" }}>{t(scrutiny?.text)}</p>
                    </Fragment>
                  )) : null}

                {/* to get Owner values */}
                {(detail?.isOwnerDetails && detail?.additionalDetails?.owners?.length > 0) ? detail?.additionalDetails?.owners.map((owner, index) => (
                  <div key={index} style={detail?.additionalDetails?.owners?.length > 1 ? { marginTop: "19px", background: "#FAFAFA", border: "1px solid #D6D5D4", borderRadius: "4px", padding: "8px", lineHeight: "19px", maxWidth: "950px", minWidth: "280px" } : {}}>
                    {detail?.additionalDetails?.owners?.length > 1 ? <Row className="border-none" label={`${t("Owner")} - ${index + 1}`} /> : null }
                    {owner?.values.map((value) => (
                      <Row className="border-none" label={t(value?.title)} text={getTranslatedValues(value?.value, value?.isNotTranslated) || t("CS_NA")} />
                    ))}
                  </div>
                )) : null}

                {/* to get Document values */}
                {(detail?.isDocumentDetails && detail?.additionalDetails?.obpsDocuments?.[0]?.values) && (
                  <div style={{marginTop: "-8px"}}>
                    {<DocumentsPreview documents={getOrderDocuments(detail?.additionalDetails?.obpsDocuments?.[0]?.values)} svgStyles = {{}} isSendBackFlow = {false} isHrLine = {true} titleStyles ={{fontSize: "20px", lineHeight: "24px", "fontWeight": 700, marginBottom: "10px"}}/>}
                  </div>
                )}

                

                
                

                {/* to get FieldInspection values */}
                {(detail?.isFieldInspection && data?.applicationData?.additionalDetails?.fieldinspection_pending?.length > 0) ? <InspectionReport isCitizen={true} fiReport={data?.applicationData?.additionalDetails?.fieldinspection_pending} /> : null}

                {/* to get NOC values */}
                {detail?.additionalDetails?.noc?.length > 0 ? detail?.additionalDetails?.noc.map((nocob, ind) => (
                  <div key={ind} style={{ marginTop: "19px", background: "#FAFAFA", border: "1px solid #D6D5D4", borderRadius: "4px", padding: "8px", lineHeight: "19px", maxWidth: "960px", minWidth: "280px" }}>
                    <StatusTable>
                      <Row className="border-none" label={t(`${`BPA_${detail?.additionalDetails?.data?.nocType}_HEADER`}`)} labelStyle={{fontSize: "20px"}}></Row>
                      <Row className="border-none" label={t(`${detail?.values?.[0]?.title}`)} textStyle={{ marginLeft: "10px" }} text={getTranslatedValues(detail?.values?.[0]?.value, detail?.values?.[0]?.isNotTranslated)} />
                      <Row className="border-none" label={t(`${detail?.values?.[1]?.title}`)} textStyle={detail?.values?.[1]?.value == "APPROVED" || detail?.values?.[1]?.value == "AUTO_APPROVED" ? { marginLeft: "10px", color: "#00703C" } : { marginLeft: "10px", color: "#D4351C" }} text={getTranslatedValues(detail?.values?.[1]?.value, detail?.values?.[1]?.isNotTranslated)} />
                      { detail?.values?.[2]?.value ? <Row className="border-none" label={t(`${detail?.values?.[2]?.title}`)} textStyle={{ marginLeft: "10px" }} text={getTranslatedValues(detail?.values?.[2]?.value, detail?.values?.[2]?.isNotTranslated)} /> : null }
                      { detail?.values?.[3]?.value ? <Row className="border-none" label={t(`${detail?.values?.[3]?.title}`)} textStyle={{ marginLeft: "10px" }} text={getTranslatedValues(detail?.values?.[3]?.value, detail?.values?.[3]?.isNotTranslated)} /> : null }
                      { detail?.values?.[3]?.value ? <Row className="border-none" label={t(`${detail?.values?.[4]?.title}`)} textStyle={{ marginLeft: "10px" }} text={getTranslatedValues(detail?.values?.[4]?.value, detail?.values?.[4]?.isNotTranslated)} /> : null }
                      <Row className="border-none" label={t(`${nocob?.title}`)}></Row>
                    </StatusTable>
                    <StatusTable>
                      {nocob?.values ? <DocumentsPreview documents={getOrderDocuments(nocob?.values, true)} svgStyles = {{}} isSendBackFlow = {false} isHrLine = {true} titleStyles ={{fontSize: "18px", lineHeight: "24px", "fontWeight": 700, marginBottom: "10px"}}/> :
                        <div><CardText>{t("BPA_NO_DOCUMENTS_UPLOADED_LABEL")}</CardText></div>}
                    </StatusTable>
                  </div>
                )) : null}

                {/* to get permit values */}
                {(!detail?.isTitleVisible && detail?.additionalDetails?.permit?.length > 0) ? detail?.additionalDetails?.permit?.map((value) => (
                  <CardText >{value?.title}</CardText>
                )) : null}

                {/* to get Fee values */}
                {detail?.additionalDetails?.inspectionReport && detail?.isFeeDetails && <ScruntinyDetails scrutinyDetails={detail?.additionalDetails} paymentsList={[]}/>}
                 {/*blocking reason*/}
                {detail?.additionalDetails?.inspectionReport && detail?.isFeeDetails && (workflowDetails?.data?.actionState?.nextActions?.[0]?.state=="POST_PAYMENT_CITIZEN_APPROVAL_PENDING"|| workflowDetails?.data?.actionState?.state=="POST_PAYMENT_CITIZEN_APPROVAL_PENDING" || workflowDetails?.data?.actionState?.state=="POST_PAYMENT_INPROGRESS") &&
                <div style={ { marginTop: "19px", background: "#FAFAFA", border: "1px solid #D6D5D4", borderRadius: "4px", padding: "8px", lineHeight: "19px", maxWidth: "950px", minWidth: "280px"} }>
                  <Row className="border-none" label={t(`BLOCKING_REASON`)} labelStyle={{fontSize: "15px"}} text={data?.applicationData.additionalDetails.blockingReason || "NA"}> </Row>
                </div>
                }
              </StatusTable>
              </div>
            </Card> : null }

            

            {/* to get Timeline values */}
            {index === arr.length - 1 && (
              <Card>
                <Fragment>
                  <div id="timeline">
                  <BPAApplicationTimeline application={data?.applicationData} id={id} />
                  {!workflowDetails?.isLoading && workflowDetails?.data?.nextActions?.length > 0 && !isFromSendBack && checkBoxVisible && (
                    <CheckBox
                      styles={{ margin: "20px 0 40px", paddingTop: "10px" }}
                      checked={isTocAccepted}
                      label={getCheckBoxLable()}
                      // label={getCheckBoxLabelData(t, data?.applicationData, workflowDetails?.data?.nextActions)}
                      onChange={() => { setIsTocAccepted(!isTocAccepted); isTocAccepted ? setDisplayMenu(!isTocAccepted) : "" }}
                    />
                  )}
                  </div>
                  {!workflowDetails?.isLoading && workflowDetails?.data?.nextActions?.length > 1 && (
                    //removed this styles to fix the action button in application details UM-5347
                    <ActionBar /*style={{ position: "relative", boxShadow: "none", minWidth: "240px", maxWidth: "310px", padding: "0px" }}*/>
                      <div style={{ width: "100%" }}>
                        {displayMenu && workflowDetails?.data?.nextActions ? (
                          <Menu
                            //style={{ bottom: "37px", minWidth: "240px", maxWidth: "310px", width: "100%", right: "0px" }}
                            localeKeyPrefix={"WF_BPA"}
                            options={workflowDetails?.data?.nextActions.map((action) => action.action)}
                            t={t}
                            onSelect={onActionSelect}
                          />
                        ) : null}
                        <SubmitBar /*style={{ width: "100%" }}*/ disabled={checkForSubmitDisable(isFromSendBack, isTocAccepted) || (workflowDetails?.data?.actionState?.state ==="CITIZEN_APPROVAL_PENDING" ? (!agree || !isOTPVerified || !citizenvalidations) : false )} label={t("ES_COMMON_TAKE_ACTION")} onSubmit={() => setDisplayMenu(!displayMenu)} />
                      </div>
                    </ActionBar>
                  )}
                  {!workflowDetails?.isLoading && workflowDetails?.data?.nextActions?.length == 1 && (
                    //removed this style to fix the action button in application details UM-5347
                    <ActionBar /*style={{ position: "relative", boxShadow: "none", minWidth: "240px", maxWidth: "310px", padding: "0px" }}*/>
                      <div style={{ width: "100%" }}>
                        <button 
                        style={{  color: "#FFFFFF", fontSize: isMobile ? "19px" : "initial" }}
                        className={`${checkForSubmitDisable(isFromSendBack, isTocAccepted) ? "submit-bar-disabled" : "submit-bar"}`}
                        disabled={checkForSubmitDisable(isFromSendBack, isTocAccepted)} 
                        name={workflowDetails?.data?.nextActions?.[0]?.action} 
                        value={workflowDetails?.data?.nextActions?.[0]?.action}
                        onClick={(e) => {onActionSelect(e.target.value)}}>
                        {t(`WF_BPA_${workflowDetails?.data?.nextActions?.[0]?.action}`)}
                        </button>
                      </div>
                    </ActionBar>
                  )}
                </Fragment>
              </Card>
            )}
          </div>
        )
      })}

      

      
     

      {workflowDetails?.data?.actionState?.state ==="CITIZEN_APPROVAL_PENDING" && (

      <div>
        <Card>
        <React.Fragment>
              <div>
            <CardLabel>{t("ARCHITECT_SHOULD_VERIFY_HIMSELF_BY_CLICKING_BELOW_BUTTON")}</CardLabel>
            <SubmitBar label={t("BPA_VERIFY")} onSubmit={handleVerifyClick} />
            <br></br>
            {showMobileInput && (
              <React.Fragment>
                <br></br>
              <CardLabel>{t("BPA_MOBILE_NUMBER")}</CardLabel>
              <TextInput
                t={t}
                type="tel"
                isMandatory={true}
                optionKey="i18nKey"
                name="mobileNumber"
                value={mobileNumber}
                onChange={handleMobileNumberChange}
                {...{ required: true, pattern: "[0-9]{10}", type: "tel", title: t('CORE_COMMON_APPLICANT_MOBILE_NUMBER_INVALID') }}
              />
        
              <SubmitBar label={t("BPA_GET_OTP")} onSubmit={handleGetOTPClick} disabled={!isValidMobileNumber} />
              </React.Fragment>
              )}
              {showOTPInput && (
                <React.Fragment>
                  <br></br>
                  <CardLabel>{t('BPA_OTP')}</CardLabel>
                  <TextInput
                    t={t}
                    type="text"
                    isMandatory={true}
                    optionKey="i18nKey"
                    name="otp"
                    value={otp}
                    onChange={handleOTPChange}
                    {...{ required: true, pattern: "[0-9]{6}", type: "tel", title: t('BPA_INVALID_OTP') }}
                  />

                  <SubmitBar label={t("VERIFY_OTP")} onSubmit={handleVerifyOTPClick} />
                  {otpError && <CardLabel style={{ color: 'red' }}>{otpError}</CardLabel>}
                </React.Fragment>
              )}
          </div>
          <br></br>

          
          <div>
          <CheckBox
              label={checkLabels()}
              onChange={setdeclarationhandler}
              styles={{ height: "auto" }}
              //disabled={!agree}
            />
            
            {showTermsPopup && (
            <CitizenConsent
              showTermsPopup={showTermsPopup}
              setShowTermsPopup={setShowTermsPopup}
              otpVerifiedTimestamp={otpVerifiedTimestamp} // Pass timestamp as a prop
            />
          )}
          </div>

        </React.Fragment>
        </Card>
      </div>
        

      )}

      {showTermsModal ? (
        <ActionModal
          t={t}
          action={"TERMS_AND_CONDITIONS"}
          tenantId={tenantId}
          id={id}
          closeModal={closeTermsModal}
          submitAction={submitAction}
          applicationData={data?.applicationData || {}}
        />
      ) : null}
      {showModal ? (
        <ActionModal
          t={t}
          action={selectedAction}
          tenantId={tenantId}
          // state={state}
          id={id}
          closeModal={closeModal}
          submitAction={submitAction}
          actionData={workflowDetails?.data?.timeline}
        />
      ) : null}
      {showToast && (
        <Toast
          error={showToast.key === "error" ? true : false}
          label={t(showToast.key === "success" ? `ES_OBPS_${showToast.action}_UPDATE_SUCCESS` : showToast.action)}
          onClose={closeToast}
          style={{ zIndex: "1000" }}
        />
      )}
    </Fragment>
  );
};

export default BpaApplicationDetail;