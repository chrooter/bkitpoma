package com.bkitmobile.poma.client.ui.map;

import java.util.Vector;

import com.bkitmobile.poma.client.database.WayPoint;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerMouseOutHandler;
import com.google.gwt.maps.client.event.MarkerMouseOverHandler;
import com.google.gwt.maps.client.event.PolylineClickHandler;
import com.google.gwt.maps.client.event.PolylineMouseOutHandler;
import com.google.gwt.maps.client.event.PolylineMouseOverHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.PolyStyleOptions;
import com.google.gwt.maps.client.overlay.Polyline;

public class TrackPointOverlay extends Polyline {

	private Vector<WayPointOverlay> wayPoints = new Vector<WayPointOverlay>();
	private boolean isChoosen = false;
	private static PolyStyleOptions TRACKPOINT_STYLE_NORMAL = PolyStyleOptions
			.newInstance("GREEN");
	private static PolyStyleOptions TRACKPOINT_STYLE_CHOOSEN = PolyStyleOptions
			.newInstance("RED");
	private static PolyStyleOptions TRACKPOINT_STYLE_HOVER = PolyStyleOptions
			.newInstance("YELLOW");

	WayPointClickListener clickListener = new WayPointClickListener();
	WayPointMouseOverListener mouseOverListener = new WayPointMouseOverListener();
	WayPointMouseOutListener mouseOutListener = new WayPointMouseOutListener();

	public TrackPointOverlay(WayPoint[] wayPoints, String[] foregroundURL,
			String[] backgroundURL) {
		// TODO Auto-generated constructor stub
		super(getLatLngArray(wayPoints));

		if (wayPoints.length == foregroundURL.length
				&& wayPoints.length == backgroundURL.length) {
			for (int i = 0; i < wayPoints.length; i++) {
				WayPointOverlay overlay = new WayPointOverlay(wayPoints[i],
						foregroundURL[i], backgroundURL[i]);
				overlay.addMarkerClickHandler(clickListener);
				overlay.addMarkerMouseOverHandler(mouseOverListener);
				overlay.addMarkerMouseOutHandler(mouseOutListener);
				this.wayPoints.add(overlay);
			}
		}

		setListener();
		setStrokeStyle(TRACKPOINT_STYLE_NORMAL);
	}

	public TrackPointOverlay(WayPoint[] wayPoints, String foregroundURL,
			String backgroundURL) {
		// TODO Auto-generated constructor stub
		super(getLatLngArray(wayPoints));

		for (int i = 0; i < wayPoints.length; i++) {
			WayPointOverlay overlay = new WayPointOverlay(wayPoints[i],
					foregroundURL, backgroundURL);
			overlay.addMarkerClickHandler(clickListener);
			overlay.addMarkerMouseOverHandler(mouseOverListener);
			overlay.addMarkerMouseOutHandler(mouseOutListener);
			this.wayPoints.add(overlay);
		}

		setListener();
		setStrokeStyle(TRACKPOINT_STYLE_NORMAL);

	}

	public TrackPointOverlay(WayPoint[] wayPoints) {
		// TODO Auto-generated constructor stub
		super(getLatLngArray(wayPoints));

		for (int i = 0; i < wayPoints.length; i++) {
			WayPointOverlay overlay = new WayPointOverlay(wayPoints[i]);
			overlay.addMarkerClickHandler(clickListener);
			overlay.addMarkerMouseOverHandler(mouseOverListener);
			overlay.addMarkerMouseOutHandler(mouseOutListener);
			this.wayPoints.add(overlay);
		}

		setListener();
		setStrokeStyle(TRACKPOINT_STYLE_NORMAL);
	}

	private static LatLng[] getLatLngArray(WayPoint[] wayPoints) {
		LatLng[] latlngArray = new LatLng[wayPoints.length];
		for (int i = 0; i < latlngArray.length; i++) {
			latlngArray[i] = wayPoints[i].getLatLng();
		}
		return latlngArray;
	}

	private void setTrackpointState() {
		if (isChoosen) {
			setStrokeStyle(TRACKPOINT_STYLE_CHOOSEN);
		} else {
			setStrokeStyle(TRACKPOINT_STYLE_NORMAL);
		}
	}

	private void setListener() {
		addPolylineClickHandler(new PolylineClickHandler() {
			@Override
			public void onClick(PolylineClickEvent event) {
				// TODO Auto-generated method stub
				isChoosen = !isChoosen;
				setTrackpointState();
			}
		});
		addPolylineMouseOverHandler(new PolylineMouseOverHandler() {
			@Override
			public void onMouseOver(PolylineMouseOverEvent event) {
				// TODO Auto-generated method stub
				setStrokeStyle(TRACKPOINT_STYLE_HOVER);
			}
		});
		addPolylineMouseOutHandler(new PolylineMouseOutHandler() {
			@Override
			public void onMouseOut(PolylineMouseOutEvent event) {
				// TODO Auto-generated method stub
				setTrackpointState();
			}
		});
	}

	public void addWayPointOverlayToMapWidget(MapWidget mapwidget) {
		for (int i = 0; i < wayPoints.size(); i++) {
			mapwidget.addOverlay(wayPoints.elementAt(i));
		}
	}

	public void addWayPoint(WayPoint point, String foregroundURL,
			String backgroundURL, MapWidget mapWidget) {
		// WayPointOverlay overlay = new WayPointOverlay(point, foregroundURL,
		// backgroundURL);
		// wayPoints.add(overlay);
		// insertVertex(wayPoints.size(), point.getLatLng());
		// mapWidget.addOverlay(overlay);
		addWayPoint(point, foregroundURL, backgroundURL, wayPoints.size(),
				mapWidget);
	}

	public void addWayPoint(WayPoint point, String foregroundURL,
			String backgroundURL, int index, MapWidget mapWidget) {
		WayPointOverlay overlay = new WayPointOverlay(point, foregroundURL,
				backgroundURL);
		wayPoints.add(overlay);
		wayPoints.add(index, overlay);

		overlay.addMarkerClickHandler(clickListener);
		overlay.addMarkerMouseOverHandler(mouseOverListener);
		overlay.addMarkerMouseOutHandler(mouseOutListener);

		insertVertex(index, point.getLatLng());
		mapWidget.addOverlay(overlay);
	}

	public void setTrackPointNormalStyleColor(String color) {
		TRACKPOINT_STYLE_NORMAL.setColor(color);
	}

	public void setTrackPointHoverStyleColor(String color) {
		TRACKPOINT_STYLE_HOVER.setColor(color);
	}

	public void setTrackPointChoosenStyleColor(String color) {
		TRACKPOINT_STYLE_CHOOSEN.setColor(color);
	}

	public void setTrackPointNormalStyleWeight(int weight) {
		TRACKPOINT_STYLE_NORMAL.setWeight(weight);
	}

	public void setTrackPointHoverStyleWeight(int weight) {
		TRACKPOINT_STYLE_HOVER.setWeight(weight);
	}

	public void setTrackPointChoosenStyleWeight(int weight) {
		TRACKPOINT_STYLE_CHOOSEN.setWeight(weight);
	}

	public void setTrackPointNormalStyleOpacity(int opacity) {
		TRACKPOINT_STYLE_NORMAL.setOpacity(opacity);
	}

	public void setTrackPointHoverStyleOpacity(int opacity) {
		TRACKPOINT_STYLE_HOVER.setOpacity(opacity);
	}

	public void setTrackPointChoosenStyleOpacity(int opacity) {
		TRACKPOINT_STYLE_CHOOSEN.setOpacity(opacity);
	}

	class WayPointClickListener implements MarkerClickHandler {

		@Override
		public void onClick(MarkerClickEvent event) {
			// TODO Auto-generated method stub
			isChoosen = !isChoosen;
			setTrackpointState();
		}
	}

	class WayPointMouseOverListener implements MarkerMouseOverHandler {

		@Override
		public void onMouseOver(MarkerMouseOverEvent event) {
			// TODO Auto-generated method stub
			setStrokeStyle(TRACKPOINT_STYLE_HOVER);
		}
	}

	class WayPointMouseOutListener implements MarkerMouseOutHandler {

		@Override
		public void onMouseOut(MarkerMouseOutEvent event) {
			// TODO Auto-generated method stub
			setTrackpointState();
		}
	}
}
