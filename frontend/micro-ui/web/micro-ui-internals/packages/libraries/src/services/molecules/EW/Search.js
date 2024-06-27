import { values } from "lodash";
import { EwService } from "../../elements/EW";

export const EWSearch = {
  all: async (tenantId, filters = {}) => {
    console.log("filterrsrs",filters)
    
    const response = await EwService.search({ tenantId, filters });
    
    return response;
  },

  application: async (tenantId, filters = {}) => {
    const response = await EwService.search({ tenantId, filters });
    console.log("response",response);
    return response.EwasteApplication[0];
  },

  RegistrationDetails: ({ EwasteApplication: response, t }) => {
    console.log("responseeeeee",response);

  const productRows = response?.ewasteDetails?.map((product) => (
    [
      product?.productName,
      product?.quantity,
      product?.price,
      product?.price * product?.quantity,
    ]
  )) || [];

  // console.log("productRow", productRows)


    return [
      {
        title: "EW_APPLICANT_DETAILS",
        asSectionHeader: true,
        values: [
          { title: "EW_APPLICANT_NAME", value: response?.applicant?.applicantName },
          { title: "EW_EMAIL", value: response?.applicant?.emailId },
          { title: "EW_APPLICATION_NUMBER", value: response?.requestId },
          { title: "EW_MOBILE_NUMBER", value: response?.applicant?.mobileNumber },
          { title: "EW_ALT_NUMBER", value: response?.applicant?.altMobileNumber },
        ],
      },
      {
        title: "EW_ADDRESS_DETAILS",
        asSectionHeader: true,
        values: [
          { title: "EW_PINCODE", value: response?.address?.pincode },
          { title: "EW_CITY", value: response?.address?.city },
          { title: "EW_DOOR_NO", value: response?.address?.doorNo },
          { title: "EW_STREET", value: response?.address?.street },
          { title: "EW_ADDRESS_LINE_1", value: response?.address?.addressLine1 },
          { title: "EW_ADDRESS_LINE_2", value: response?.address?.addressLine2 },
          { title: "EW_BUILDING_NAME", value: response?.address?.buildingName },
        ],
      },
      {
        title:"EW_PRODUCT_DETAILS",
        asSectionHeader: true,
        isTable: true,
        headers: ["PRODUCT_NAME", "PRODUCT_QUANTITY", "UNIT_PRICE", "TOTAL_PRODUCT_PRICE"],
        tableRows: productRows,
        values: [
          { title: "", value: " "},
          { title: "EWASTE_NET_PRICE", value: response?.calculatedAmount},
        ]
      },
    ];
  },

  applicationDetails: async (t, tenantId, requestId, userType, args) => {
    const filter = { requestId, ...args };
    const response = await EWSearch.application(tenantId, filter);
    
    return {
      tenantId: response.tenantId,
      applicationDetails: EWSearch.RegistrationDetails({ EwasteApplication: response, t }),
      applicationData: response,
      transformToAppDetailsForEmployee: EWSearch.RegistrationDetails,
    };
  },
};
