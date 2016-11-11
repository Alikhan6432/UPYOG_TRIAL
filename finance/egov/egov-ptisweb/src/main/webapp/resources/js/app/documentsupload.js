/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
jQuery(document)
		.ready(
				function() {

					var fileformatsinclude = [ 'doc', 'docx', 'xls', 'xlsx',
							'rtf', 'pdf', 'jpeg', 'jpg', 'png', 'txt', 'xml' ];

					jQuery('.doctable input:file')
							.change(
									function(e) {
										var fileName = jQuery(this).val();
										var ext = fileName.split('.').pop();
										if (jQuery.inArray(ext.toLowerCase(),
												fileformatsinclude) > -1) {
										} else {
											bootbox
													.alert("Please upload .doc, .docx, .xls, .xlsx, .rtf, .pdf, jpeg, .jpg, .png, .txt and .xml format documents only");
											jQuery(this).val('');
											return false;
										}
										var fileInput = jQuery(this);
										var maxSize = 2097152; // file size in
										// bytes(2MB)
										var inMB = maxSize / 1024 / 1024;
										if (fileInput.get(0).files.length) {
											var fileSize = this.files[0].size; // in
											// bytes
											var charlen = (this.value
													.split('/').pop().split(
															'\\').pop()).length;
											if (charlen > 50) {
												bootbox
														.alert('File length should not exceed 50 characters!');
												fileInput.replaceWith(fileInput
														.val('').clone(true));
												return false;
											} else if (fileSize > maxSize) {
												bootbox
														.alert('File size should not exceed '
																+ inMB + ' MB!');
												fileInput.replaceWith(fileInput
														.val('').clone(true));
												return false;
											}
										}

									});

				});
function addFileInputField() {
	var uploaderTbl = document.getElementById("uploadertbl");
	var tbody = uploaderTbl.lastChild;
	var trNo = (tbody.childElementCount ? tbody.childElementCount
			: tbody.childNodes.length) + 1;
	var tempTrNo = trNo;
	var flag = false;
	for (i = 0; i < tempTrNo; i++) {
		var checkboxValue = document.getElementById('check' + i).checked;
		var curFieldValue = $("#file" + i).val();
		if (curFieldValue == "" && checkboxValue == true) {
			bootbox.alert("Please attach the File");
			flag = true;
			return flag;
		}
	}
	return flag;
}
