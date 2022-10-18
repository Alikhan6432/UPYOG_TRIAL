import React, { useState,useEffect } from "react";
import { CardLabel, TypeSelectCard,Dropdown } from "@egovernments/digit-ui-react-components";
import { FormStep, RadioOrSelect, RadioButtons } from "@egovernments/digit-ui-react-components";
import Timeline from "../components/TLTimeline";

const SelectStructureType = ({ t, config, onSelect, userType, formData }) => {
  const stateId = Digit.ULBService.getStateId();
  const { data: dataitem = {}, isLoading } = Digit.Hooks.tl.useTradeLicenseMDMS(stateId, "TradeLicense", "TradeStructureSubtype");
  const [setPlaceofActivity, setSelectedPlaceofActivity] = useState(formData?.TradeDetails?.setPlaceofActivity);
  const [StructureType, setStructureType] = useState(formData?.TradeDetails?.StructureType);
  const [activities, setActivity] = useState(0);
  const isEdit = window.location.href.includes("/edit-application/")||window.location.href.includes("renew-trade");
  let naturetype =null;
  let naturetypecmbvalue =null;
  let routeComponent =null;
  let cmbPlace = [];
  let cmbStructure = [];
  dataitem &&
  dataitem["TradeLicense"] &&
    dataitem["TradeLicense"].TradeStructureSubtype.map((ob) => {
        cmbPlace.push(ob);
    });
  const menu = [
    { i18nKey: "TL_COMMON_LAND", code: "LAND" },
    { i18nKey: "TL_COMMON_BUILDING", code: "BUILDINg" },
  ];
 
  const onSkip = () => onSelect();

  function selectPlaceofactivity(value) {
    naturetypecmbvalue=value.code.substring(0, 4);
    setSelectedPlaceofActivity(value);
    setStructureType(null);
    setActivity(null);
    console.log(naturetypecmbvalue);    
     if(naturetypecmbvalue =="LAND"){      
        routeComponent = "land-type";
        console.log(routeComponent);        
      } else if(naturetypecmbvalue =="BUIL"){
        routeComponent = "building-det";
        console.log(routeComponent);
      }    
      // sessionStorage.removeItem("routeElement");
      sessionStorage.setItem("routeElement", routeComponent);
      // onSelect(config.key, { routeElement });
  }
  function selectStructuretype(value) {
    setStructureType(value);
    
  }

  useEffect(() => {
    if(setPlaceofActivity){
      // console.log(setPlaceofActivity);
      naturetype = setPlaceofActivity.code.substring(0, 4);    
      setActivity(cmbPlace.filter( (cmbPlace) => cmbPlace.code.includes(naturetype)));
      // console.log(naturetype);     
    }
  }, [activities]);
 
  function goNext() {
   
    sessionStorage.setItem("setPlaceofActivity", setPlaceofActivity.mainName);
    onSelect(config.key, { setPlaceofActivity });
    sessionStorage.setItem("StructureType", StructureType.name);
    onSelect(config.key, { StructureType });
    onSelect(config.key, { routeElement });
    
  }
  return (
    <React.Fragment>
    {window.location.href.includes("/citizen") ? <Timeline /> : null}
    <FormStep t={t} config={config} onSelect={goNext} onSkip={onSkip} isDisabled={!StructureType}>
      
      <CardLabel>Place Of Activity</CardLabel>
      <Dropdown
        t={t}
        optionKey="mainName"
        isMandatory={config.isMandatory}
        option={cmbPlace}
        selected={setPlaceofActivity}
        select={selectPlaceofactivity}
        disabled={isEdit}
      />
      {/* <RadioOrSelect 
          options={menu}
          selectedOption={PlaceofActivity}
          optionKey="mainName"
          onSelect={selectPlaceofactivity}
          t={t}
          labelKey=""
          // onChange={(e) => onChangeLB(e.target.value)}
        //  disabled={isEdit}
        /> */}

      <CardLabel>Nature Of Structure</CardLabel>
      <Dropdown
        t={t}
        optionKey="name"
        isMandatory={config.isMandatory}
        option={activities}
        selected={StructureType}
        select={selectStructuretype}
        disabled={isEdit}
      />
      {/* <RadioOrSelect 
          options={activities}
          selectedOption={StructureType}
          optionKey="name"
          onSelect={selectStructuretype}
          t={t}
          labelKey=""
          // onChange={(e) => onChangeLB(e.target.value)}
        //  disabled={isEdit}
        /> */}
      {/* <RadioButtons
        t={t}
        optionsKey="i18nKey"
        isMandatory={config.isMandatory}
        options={menu}
        selectedOption={StructureType}
        onSelect={selectStructuretype}
        disabled={isEdit}
      /> */}
    </FormStep>
    </React.Fragment>
  );
};
export default SelectStructureType;
