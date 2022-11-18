import React, { Fragment } from "react";
import { ArrowRightInbox } from "./svgindex";
import { Link } from "react-router-dom";

const EmployeeModuleCard = ({ Icon, moduleName, kpis = [], links = [], isCitizen = false, className, styles, FsmHideCount }) => {
  return (
    <div className={className ? className : "employeeCard card-home customEmployeeCard"} style={styles ? styles : {}}>
      {/* <div className="complaint-links-container">
        <div className="header" style={isCitizen ? { padding: "0px" } : {}}>
          <span className="text removeHeight">{moduleName}</span>
          <span className="logo removeBorderRadiusLogo">{Icon}</span>
        </div>
        <div className="body" style={{ margin: "0px", padding: "0px" }}>
          {kpis.length !== 0 && (
            <div className="flex-fit" style={isCitizen ? { paddingLeft: "17px" } : {}}>
              {kpis.map(({ count, label, link }, index) => (
                <div className="card-count" key={index}>
                  <div>
                    <span>{count || "-"}</span>
                  </div>
                  <div>
                    {link ? (
                      <Link to={link} className="employeeTotalLink">
                        {label}
                      </Link>
                    ) : null}
                  </div>
                </div>
              ))}
            </div>
          )}
          <div className="links-wrapper" style={{ width: "80%" }}>
            {links.map(({ count, label, link }, index) => (
              <span className="link" key={index}>
                {link ? <Link to={link}>{label}</Link> : null}
                {count ? (
                  <>
                    {FsmHideCount ? null : <span className={"inbox-total"}>{count || "-"}</span>}
                    <Link to={link}>
                      <ArrowRightInbox />
                    </Link>
                  </>
                ) : null}
              </span>
            ))}
          </div>
        </div>
      </div> */}
      <div className="employeeCustomCard" style={{ width: "100%", height: "85%", position: "relative" }}>
        <span className="text-employee-card">{moduleName}</span>
        <span className="logo-removeBorderRadiusLogo" style={{ position: "absolute", right: "10%", top: "10%" }}>{Icon}</span>
        <div className="employee-card-banner">
          <div className="body" style={{ margin: "0px", padding: "0px" }}>
            {kpis.length !== 0 && (
              <div className="flex-fit" style={isCitizen ? { paddingLeft: "17px" } : {}}>

                {kpis.map(({ count, label, link }, index) => (
                  <div className="card-count" key={index} style={{ display: "flex", width: "100%" }}>
                    <div style={{ width: "50px", height: "50px" }}><span className="icon-banner-employee" style={{ position: "absolute", left: "10%", top: "10%", borderRadius: "5px", boxShadow: "5px 5px 5px 0px #e3e4e3" }}>{Icon}</span></div>
                    <div style={{ marginLeft: "auto", display: "flex", flexDirection: "row-reverse", width: "100%" }}>

                      <button type="button" class="inboxButton">
                        {link ? (
                          <Link to={link} className="employeeTotalLink">
                            {label}
                          </Link>
                        ) : null}
                      </button>
                      <div style={{ padding: "10px 15px" }}>
                        <span style={{ color: "#ae1e28", fontSize: "18px", fontFamily: "sans-serif", fontWeight: "bold" }}>{count || "-"}</span>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
            <div className="links-wrapper" style={{ width: "100%", display: "flex", fontSize: "0.8rem", padding: "5px", paddingLeft: "10px" }}>
              {links.map(({ count, label, link }, index) => (
                <span className="link" key={index} style={{ paddingLeft: "5px", color: "#a1a5b7" }}>
                  {link ? <Link to={link}>{label} |</Link> : null}
                </span>

              ))}
            </div>
          </div>

        </div>
      </div>
      
      <div>
      </div>
    </div>
  );
};

const ModuleCardFullWidth = ({ moduleName, links = [], isCitizen = false, className, styles, headerStyle, subHeader, subHeaderLink }) => {
  return (
    <div className={className ? className : "employeeCard card-home customEmployeeCard"} style={styles ? styles : {}}>
      <div className="complaint-links-container" style={{ padding: "10px" }}>
        <div className="header" style={isCitizen ? { padding: "0px" } : headerStyle}>
          <span className="text removeHeight">{moduleName}</span>
          <span className="link">
            <a href={subHeaderLink}>
              <span className={"inbox-total"} style={{ display: "flex", alignItems: "center", color: "#a82227", fontWeight: "bold" }}>
                {subHeader || "-"}
                <span style={{ marginLeft: "10px" }}>
                  {" "}
                  <ArrowRightInbox />
                </span>
              </span>
            </a>
          </span>
        </div>
        <div className="body" style={{ margin: "0px", padding: "0px" }}>
          <div className="links-wrapper" style={{ width: "100%", display: "flex", flexWrap: "wrap" }}>
            {links.map(({ count, label, link }, index) => (
              <span className="link full-employee-card-link" key={index}>
                {link ? link?.includes("digit-ui/") ? <Link to={link}>{label}</Link> : <a href={link}>{label}</a> : null}
              </span>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export { EmployeeModuleCard, ModuleCardFullWidth };
