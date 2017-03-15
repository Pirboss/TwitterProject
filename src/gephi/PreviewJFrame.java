/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package gephi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.EdgeIterable;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.preview.api.*;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

/**
 *
 * @author Mathieu Bastian
 */
public class PreviewJFrame{
    
    public void script() {
        
        
        //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        //Import file
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        Container container;
        try {
            //File file = new File(getClass().getResource("/org/gephi/toolkit/demos/Java.gexf").toURI());
            File file = new File("graphFollowers.gexf");
            container = importController.importFile(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        //Append imported data to GraphAPI
        importController.process(container, new DefaultProcessor(), workspace);
        
        

        //Preview configuration
        PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
        PreviewModel previewModel = previewController.getModel();
        previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
        previewModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.BLUE));
        previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
        //previewModel.getProperties().putValue(PreviewProperty.EDGE_RESCALE_WEIGHT, Boolean.TRUE);
        //previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);
        //previewModel.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.YELLOW);
        //previewModel.getProperties().putValue(PreviewProperty.SHOW_EDGES, Boolean.TRUE);
        //previewModel.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.RED));
        //previewModel.getProperties().putValue(PreviewProperty.EDGE_LABEL_COLOR, Color.RED);
        //previewModel.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, 3);
        //New Processing target, get the PApplet
        
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel(workspace);
        
        DirectedGraph directedGraph = graphModel.getDirectedGraph();
        for (Edge e : directedGraph.getEdges().toArray()) {
            e.setColor(Color.yellow);
            System.out.println(e.getSource().getId() + " -> " + e.getTarget().getId());
            
        }
        
        
        /*Item[] lesEdges = previewModel.getItems(previewModel);
        System.out.println(lesEdges.length==0);
        for (Item lesEdge : lesEdges) {
            System.out.println("coucou\n");
        }*/
        /*GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
        Graph g = graphModel.getGraph();
        System.out.println("Nodes: " + g.getNodeCount());
        System.out.println("Edges: " + g.getEdgeCount());
        for(Edge e : g.getEdges()){
        System.out.println(e.getColor());
        e.setColor(Color.YELLOW);
        System.out.println(e.getColor());
        }*/
        
        
        /*ExportController ec = Lookup.getDefault().lookup(ExportController.class);
        try{
            ec.exportFile( new File("aaa.gexf"));
        } catch(IOException ex) {
            ex.printStackTrace();
            return;
        }*/
        
        
        G2DTarget target = (G2DTarget) previewController.getRenderTarget(RenderTarget.G2D_TARGET);
        final PreviewSketch previewSketch = new PreviewSketch(target);
        previewController.refreshPreview();

        //Add the applet to a JFrame and display
        JFrame frame = new JFrame("Test Preview");
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(previewSketch, BorderLayout.CENTER);

        frame.setSize(1024, 768);
        
        //Wait for the frame to be visible before painting, or the result drawing will be strange
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                previewSketch.resetZoom();
            }
        });
        frame.setVisible(true);
    }
}
