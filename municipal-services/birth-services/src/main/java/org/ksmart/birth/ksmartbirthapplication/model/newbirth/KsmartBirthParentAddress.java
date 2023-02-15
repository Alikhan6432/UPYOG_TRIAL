package org.ksmart.birth.ksmartbirthapplication.model.newbirth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KsmartBirthParentAddress {

    @Size(max = 64)
    @JsonProperty("presentaddressCountry")
    private String presentaddressCountry;

    @Size(max = 64)
    @JsonProperty("presentaddressStateName")
    private String presentaddressStateName;

    @Size(max = 64)
    @JsonProperty("presentInsideKeralaLBName")
    private String presentInsideKeralaLBName;

    @Size(max = 64)
    @JsonProperty("presentInsideKeralaDistrict")
    private String presentInsideKeralaDistrict;

    @Size(max = 64)
    @JsonProperty("presentInsideKeralaTaluk")
    private String presentInsideKeralaTaluk;

    @Size(max = 64)
    @JsonProperty("presentInsideKeralaVillage")
    private String presentInsideKeralaVillage;

    @Size(max = 1000)
    @JsonProperty("presentInsideKeralaLocalityNameEn")
    private String presentInsideKeralaLocalityNameEn;

    @Size(max = 2000)
    @JsonProperty("presentInsideKeralaStreetNameEn")
    private String presentInsideKeralaStreetNameEn;

    @Size(max = 2500)
    @JsonProperty("presentInsideKeralaHouseNameEn")
    private String presentInsideKeralaHouseNameEn;

    @Size(max = 1000)
    @JsonProperty("presentInsideKeralaLocalityNameMl")
    private String presentInsideKeralaLocalityNameMl;

    @Size(max = 2000)
    @JsonProperty("presentInsideKeralaStreetNameMl")
    private String presentInsideKeralaStreetNameMl;

    @Size(max = 2500)
    @JsonProperty("presentInsideKeralaHouseNameMl")
    private String presentInsideKeralaHouseNameMl;

    @Size(max = 10)
    @JsonProperty("presentInsideKeralaPincode")
    private String presentInsideKeralaPincode;

    @Size(max = 64)
    @JsonProperty("presentInsideKeralaPostOffice")
    private String presentInsideKeralaPostOffice;

    @Size(max = 64)
    @JsonProperty("presentWardNo")
    private String presentWardNo;

    @Size(max = 64)
    @JsonProperty("presentOutsideKeralaDistrict")
    private String presentOutsideKeralaDistrict;

    @Size(max = 1000)
    @JsonProperty("presentOutsideKeralaTaluk")
    private String presentOutsideKeralaTalukName;

    @Size(max = 1000)
    @JsonProperty("presentOutsideKeralaVillage")
    private String presentOutsideKeralaVillageName;


    @Size(max = 64)
    @JsonProperty("presentOutsideKeralaCityVilgeEn")
    private String presentOutsideKeralaCityVilgeEn;

    @Size(max = 64)
    @JsonProperty("presentOutsideKeralaPincode")
    private String presentOutsideKeralaPincode;
    @Size(max = 64)
    @JsonProperty("presentOutsideKeralaPostOfficeEn")
    private String presentOutsideKeralaPostOfficeEn;

    @Size(max = 64)
    @JsonProperty("presentOutsideKeralaPostOfficeMl")
    private String presentOutsideKeralaPostOfficeMl;

    @Size(max = 1000)
    @JsonProperty("presentOutsideKeralaLocalityNameEn")
    private String presentOutsideKeralaLocalityNameEn;

    @Size(max = 2000)
    @JsonProperty("presentOutsideKeralaStreetNameEn")
    private String presentOutsideKeralaStreetNameEn;

    @Size(max = 2500)
    @JsonProperty("presentOutsideKeralaHouseNameEn")
    private String presentOutsideKeralaHouseNameEn;

    @Size(max = 1000)
    @JsonProperty("presentOutsideKeralaLocalityNameMl")
    private String presentOutsideKeralaLocalityNameMl;

    @Size(max = 2000)
    @JsonProperty("presentOutsideKeralaStreetNameMl")
    private String presentOutsideKeralaStreetNameMl;


    @Size(max = 2500)
    @JsonProperty("presentOutsideKeralaHouseNameMl")
    private String presentOutsideKeralaHouseNameMl;

    @Size(max = 2500)
    @JsonProperty("presentOutSideIndiaAdressEn")
    private String presentOutSideIndiaAdressEn;


    @Size(max = 2500)
    @JsonProperty("presentOutSideIndiaAdressMl")
    private String presentOutSideIndiaAdressMl;


    @Size(max = 2500)
    @JsonProperty("presentOutSideIndiaAdressEnB")
    private String presentOutSideIndiaAdressEnB;


    @Size(max = 2500)
    @JsonProperty("presentOutSideIndiaAdressMlB")
    private String presentOutSideIndiaAdressMlB;

    @Size(max = 2500)
    @JsonProperty("presentOutSideIndiaProvinceEn")
    private String presentOutSideIndiaProvinceEn;

    @Size(max = 2500)
    @JsonProperty("presentOutSideIndiaProvinceMl")
    private String presentOutSideIndiaProvinceMl;

    @Size(max = 64)
    @JsonProperty("presentOutSideCountry")
    private String presentOutSideCountry;

    @JsonProperty("isPrsentAddress")
    private String isPrsentAddress;

    ////Db Fields//////

    @Size(max = 64)
    @JsonProperty("countryIdPresent")
    private String countryIdPresent;

    @Size(max = 64)
    @JsonProperty("stateIdPresent")
    private String stateIdPresent;

    @Size(max = 64)
    @JsonProperty("districtIdPresent")
    private String districtIdPresent;

    @Size(max = 64)
    @JsonProperty("pinNoPresent")
    private String pinNoPresent;

    @Size(max = 1000)
    @JsonProperty("localityEnPresent")
    private String localityEnPresent;

    @Size(max = 1000)
    @JsonProperty("localityMlPresent")
    private String localityMlPresent;

    @Size(max = 1000)
    @JsonProperty("streetNameEnPresent")
    private String streetNameEnPresent;

    @Size(max = 1000)
    @JsonProperty("streetNameMlPresent")
    private String streetNameMlPresent;

    @Size(max = 1000)
    @JsonProperty("houseNameNoEnPresent")
    private String houseNameNoEnPresent;

    @Size(max = 1000)
    @JsonProperty("houseNameNoMlPresent")
    private String houseNameNoMlPresent;

////Permanant
    @Size(max = 1000)
    @JsonProperty("permtaddressCountry")
    private String permtaddressCountry;

    @Size(max = 64)
    @JsonProperty("permtaddressStateName")
    private String permtaddressStateName;

    @Size(max = 64)
    @JsonProperty("permntInKeralaAdrLBName")
    private String permntInKeralaAdrLBName;

    @Size(max = 64)
    @JsonProperty("permntInKeralaAdrDistrict")
    private String permntInKeralaAdrDistrict;

    @Size(max = 64)
    @JsonProperty("permntInKeralaAdrTaluk")
    private String permntInKeralaAdrTaluk;

    @Size(max = 64)
    @JsonProperty("permntInKeralaAdrVillage")
    private String permntInKeralaAdrVillage;

    @Size(max = 1000)
    @JsonProperty("permntInKeralaAdrLocalityNameEn")
    private String permntInKeralaAdrLocalityNameEn;

    @Size(max = 2000)
    @JsonProperty("permntInKeralaAdrStreetNameEn")
    private String permntInKeralaAdrStreetNameEn;

    @Size(max = 2500)
    @JsonProperty("permntInKeralaAdrHouseNameEn")
    private String permntInKeralaAdrHouseNameEn;
    @Size(max = 2500)
    @JsonProperty("permntInKeralaAdrLocalityNameMl")
    private String permntInKeralaAdrLocalityNameMl;

    @Size(max = 2500)
    @JsonProperty("permntInKeralaAdrStreetNameMl")
    private String permntInKeralaAdrStreetNameMl;

    @Size(max = 64)
    @JsonProperty("permntInKeralaAdrPincode")
    private String permntInKeralaAdrPincode;

    @Size(max = 64)
    @JsonProperty("permntInKeralaAdrPostOffice")
    private String permntInKeralaAdrPostOffice;

    @Size(max = 64)
    @JsonProperty("permntInKeralaWardNo")
    private String permntInKeralaWardNo;

    @Size(max = 64)
    @JsonProperty("permntOutsideKeralaDistrict")
    private String permntOutsideKeralaDistrict;

    @Size(max = 1000)
    @JsonProperty("permntOutsideKeralaTaluk")
    private String permntOutsideKeralaTaluk;

    @Size(max = 1000)
    @JsonProperty("permntOutsideKeralaVillage")
    private String permntOutsideKeralaVillage;

    @Size(max = 64)
    @JsonProperty("permntOutsideKeralaPincode")
    private String permntOutsideKeralaPincode;

    @Size(max = 1000)
    @JsonProperty("permntOutsideKeralaLocalityNameEn")
    private String permntOutsideKeralaLocalityNameEn;

    @Size(max = 2000)
    @JsonProperty("permntOutsideKeralaStreetNameEn")
    private String permntOutsideKeralaStreetNameEn;

    @Size(max = 2500)
    @JsonProperty("permntOutsideKeralaHouseNameEn")
    private String permntOutsideKeralaHouseNameEn;

    @Size(max = 1000)
    @JsonProperty("permntOutsideKeralaLocalityNameMl")
    private String permntOutsideKeralaLocalityNameMl;

    @Size(max = 2000)
    @JsonProperty("permntOutsideKeralaStreetNameMl")
    private String permntOutsideKeralaStreetNameMl;

    @Size(max = 2500)
    @JsonProperty("permntOutsideKeralaHouseNameMl")
    private String permntOutsideKeralaHouseNameMl;

    @Size(max = 64)
    @JsonProperty("permntOutsideKeralaPostOfficeEn")
    private String permntOutsideKeralaPostOfficeEn;

    @Size(max = 64)
    @JsonProperty("permntOutsideKeralaPostOfficeMl")
    private String permntOutsideKeralaPostOfficeMl;

    @Size(max = 2500)
    @JsonProperty("PermntOutsideIndiaLineoneEn")
    private String PermntOutsideIndiaLineoneEn;

    @Size(max = 2500)
    @JsonProperty("PermntOutsideIndiaLineoneMl")
    private String PermntOutsideIndiaLineoneMl;

    @Size(max = 2500)
    @JsonProperty("PermntOutsideIndiaLinetwoEn")
    private String PermntOutsideIndiaLinetwoEn;

    @Size(max = 2500)
    @JsonProperty("PermntOutsideIndiaLinetwoMl")
    private String PermntOutsideIndiaLinetwoMl;

    @Size(max = 2500)
    @JsonProperty("PermntOutsideIndiaprovinceEn")
    private String PermntOutsideIndiaprovinceEn;

    @Size(max = 1000)
    @JsonProperty("PermntOutsideIndiaVillage")
    private String PermntOutsideIndiaVillage;

    @Size(max = 64)
    @JsonProperty("PermntOutsideIndiaCityTown")
    private String PermntOutsideIndiaCityTown;

    @Size(max = 10)
    @JsonProperty("PermanentOutsideIndiaPostCode")
    private String PermanentOutsideIndiaPostCode;

    @Size(max = 64)
    @JsonProperty("PermntOutsideIndiaCountry")
    private String PermntOutsideIndiaCountry;


    ////Db Fields//////
    @Size(max = 64)
    @JsonProperty("countryIdPermanant")
    private String countryIdPermanant;

    @Size(max = 64)
    @JsonProperty("stateIdPermanant")
    private String stateIdPermanant;

    @Size(max = 64)
    @JsonProperty("districtIdPermanant")
    private String districtIdPermanant;

    @Size(max = 64)
    @JsonProperty("pinNoPermanant")
    private String pinNoPermanant;

    @Size(max = 1000)
    @JsonProperty("localityEnPermanant")
    private String localityEnPermanant;

    @Size(max = 1000)
    @JsonProperty("localityMlPermanant")
    private String localityMlPermanant;

    @Size(max = 1000)
    @JsonProperty("streetNameEnPermanant")
    private String streetNameEnPermanant;

    @Size(max = 1000)
    @JsonProperty("streetNameMlPermanant")
    private String streetNameMlPermanant;

    @Size(max = 1000)
    @JsonProperty("houseNameNoEnPermanant")
    private String houseNameNoEnPermanant;

    @Size(max = 1000)
    @JsonProperty("houseNameNoMlPermanant")
    private String houseNameNoMlPermanant;



}































