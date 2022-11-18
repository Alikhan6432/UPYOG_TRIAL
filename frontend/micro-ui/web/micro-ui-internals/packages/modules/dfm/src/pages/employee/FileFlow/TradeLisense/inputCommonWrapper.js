import React,{useState} from "react";
import {
  UploadFile,
  DatePicker,
  TextInput,
  CheckBox,
  CardLabel,
  LabelFieldPair,
  TextArea,
  CitizenInfoLabel,
  RadioButtons,
  PitDimension,
  Dropdown
} from "@egovernments/digit-ui-react-components";
import DFMuploadFiles from './DFMuploadFiles'

const InputCommonWrapper = ({ label, type, selectOptions, onChange, value, placeholder }) => {
  const inputTypeSelect = (type,selectOptions, onChange, value, placeholder) => {
    const [dropdownValue,setDropdownValue] =useState('')
    const handleChange=(value)=>{
      setDropdownValue(value)
    }
    console.log(type);

    switch (type) {
      case "TextInput":
        return <TextInput onChange={onChange} value={value} placeholder={placeholder} />;
      case "TextArea":
        return <TextArea onChange={onChange} value={value} placeholder={placeholder} />;
      case "CheckBox":
        return <CheckBox />;
      case "DatePicker":
        return <DatePicker onChange={onChange} value={value} placeholder={placeholder} />;
      case "Dropdown":
        return (
          <Dropdown
            option={selectOptions}
            selected={selectOptions.find((sel) => sel.value === dropdownValue?.value)}
            optionKey={"label"}
            select={handleChange}
            freeze={true}
            placeholder={placeholder}
            // customSelector={
            //   <label className="cp">
            //     {prop?.t(`TENANT_TENANTS_${stringReplaceAll(Digit.SessionStorage.get("Employee.tenantId"), ".", "_")?.toUpperCase()}`)}
            //   </label>
            // }
          />
        );
      default:
        return <input type="" value="default" />;
    }
  };
  return (
    <React.Fragment>
      {" "}
      <div className="applicationWrapper">
        <div className="application-label">
          {" "}
          <label>{label}:</label>
        </div>
        <div>
          {" "}
          {inputTypeSelect(type,selectOptions, onChange, value, placeholder)}
        </div>
      </div>
    </React.Fragment>
  );
};

export default InputCommonWrapper;
