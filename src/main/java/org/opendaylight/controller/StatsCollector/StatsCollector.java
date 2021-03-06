/*
 * Copyright (C) 2014 SDN Hub

 Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 You may not use this file except in compliance with this License.
 You may obtain a copy of the License at

    http://www.gnu.org/licenses/gpl-3.0.txt

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 implied.

 *
 */

package org.opendaylight.controller;

import org.opendaylight.controller.sal.core.Node;
import org.opendaylight.controller.protocol_plugin.openflow.IOFStatisticsManager;
import org.opendaylight.controller.sal.core.NodeConnector;
import org.opendaylight.controller.sal.match.MatchType;
import org.opendaylight.controller.sal.reader.FlowOnNode;
import org.opendaylight.controller.sal.reader.NodeConnectorStatistics;
import org.opendaylight.controller.sal.utils.ServiceHelper;
import org.opendaylight.controller.statisticsmanager.IStatisticsManager;
import org.opendaylight.controller.switchmanager.ISwitchManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class StatsCollector {
    private static final Logger logger = LoggerFactory
            .getLogger(StatsCollector.class);
    private ISwitchManager switchManager = null;
    private IOFStatisticsManager ofStatsManager = null;

    public void setOFStatisticsManager(IOFStatisticsManager s) {
        this.ofStatsManager = s;
    }

    public void unsetOFStatisticsManager(IOFStatisticsManager s) {
        if (this.ofStatsManager == s) {
            this.ofStatsManager = null;
        }
    }

    void init() {
        logger.info("INIT called!");
    }

    void destroy() {
        logger.info("DESTROY called!");
    }

    void start() {
        logger.info("START called!");
        getFlowStatistics();
    }

    void stop() {
        logger.info("STOP called!");
    }

    void getFlowStatistics() {
        String containerName = "default";
        IStatisticsManager statsManager = (IStatisticsManager) ServiceHelper
                .getInstance(IStatisticsManager.class, containerName, this);

        ISwitchManager switchManager = (ISwitchManager) ServiceHelper
                .getInstance(ISwitchManager.class, containerName, this);

        for (Node node : switchManager.getNodes()) {
            System.out.println("\n\nNode: " + node);
            for (FlowOnNode flow : statsManager.getFlows(node)) {
                System.out.println(" DST: "
                        + flow.getFlow().getMatch().getField(MatchType.NW_DST)
                        + " Bytes: " + flow.getByteCount());
            }

		/* New Code */
		for(NodeConnector connector : switchManager.getNodeConnectors(node)) {
			System.out.println("Node Connector: " + connector.toString());
			System.out.println("Get Node Connector Statistics " + statsManager.getNodeConnectorStatistics(connector).toString());
		}
        }
	String s = "0000000496979b9e";
	long bi = Long.parseLong(s, 16);
        System.out.println("******Get OF Port Statistics: " + ofStatsManager.getOFPortStatistics(bi).toString() + "***********");
	return;
    }
}
