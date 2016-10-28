#!/bin/bash
EARFILE="../../egov/egov-ear/target/egov-ear-*.ear"
if [ -z $WILDFLY_HOME ]
then
	echo "WILDFLY_HOME is not set on environment variable."
	echo "To set WILDFLY_HOME environment variable, do the following"
	echo "1. Launch terminal by pressing Ctrl+Alt+T on your keyboard."
	echo "2. Enter the following command:"
	echo "	bash$ vi ~/.bashrc"
	echo "3. Depending on where you installed your WILDFLY, you will need to add the full path."
	echo "	export WILDFLY_HOME=<FULL-PATH-TO-WILDFLY>"
	echo "4. Reload the ~/.bashrc file using below command."
	echo "	bash$ source ~/.bashrc"
	exit 1;
fi
DEPLOY_FOLDER="${WILDFLY_HOME}/standalone/deployments"
echo "Copying local EAR to ${DEPLOY_FOLDER}"
cp -rp ${EARFILE} ${DEPLOY_FOLDER}/.
if [ $? -eq 0 ]
then 
	mv ${DEPLOY_FOLDER}/egov-ear-*.ear ${DEPLOY_FOLDER}/egov-ear.ear && touch ${DEPLOY_FOLDER}/egov-ear.ear.dodeploy
else
	echo "Unable to copy the EAR, please check the permission or disk space error" && exit 1;
fi
echo "Starting wildfly ...."
nohup ${WILDFLY_HOME}/bin/standalone.sh -Dspring.profiles.active=production -b 0.0.0.0 > /dev/null &
