package com.bkitmobile.poma.util.server.kml;

import java.util.List;

public class LineString extends Feature {

	private Boolean extrude;
	private Boolean tessellate;
	private AltitudeModeEnum altitudeMode;
	private List<Point> coordinates;
	
	public LineString() {}
	
	public LineString(Boolean extrude, Boolean tessellate, AltitudeModeEnum altitudeMode, List<Point> coordinates) {
		this.extrude = extrude;
		this.tessellate = tessellate;
		this.altitudeMode = altitudeMode;
		this.coordinates = coordinates;
	}
	
	public Boolean getExtrude() {
		return extrude;
	}

	public void setExtrude(Boolean extrude) {
		this.extrude = extrude;
	}

	public Boolean getTessellate() {
		return tessellate;
	}

	public void setTessellate(Boolean tessellate) {
		this.tessellate = tessellate;
	}

	public AltitudeModeEnum getAltitudeMode() {
		return altitudeMode;
	}

	public void setAltitudeMode(AltitudeModeEnum altitudeMode) {
		this.altitudeMode = altitudeMode;
	}

	public List<Point> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Point> coordinates) {
		this.coordinates = coordinates;
	}

	public void write(Kml kml) throws KmlException {
		// We validate the data
		if (coordinates == null || coordinates.size() < 2) {
			throw new KmlException("LineString must contain at least 2 points");
		}
		
		kml.println("<LineString" + getIdAndTargetIdFormatted(kml) + ">", 1);
		if (extrude != null) {
			kml.println("<extrude>" + booleanToInt(extrude) + "</extrude>");
		}
		if (tessellate != null) {
			kml.println("<tessellate>" + booleanToInt(tessellate) + "</tessellate>");
		}
		if (altitudeMode != null) {
			kml.println("<altitudeMode>" + altitudeMode + "</altitudeMode>");
		}
		if (coordinates != null) {
			kml.print("<coordinates>");
			boolean firstLoop = true;
			for (Point point : coordinates) {
				if (firstLoop) {
					firstLoop = false;
				} else {
					kml.print(" ");
				}
				kml.print(point.getLongitudeLatitudeAltitudeString());
			}
			kml.println("</coordinates>");
		}
		kml.println(-1, "</LineString>");
	}
	
	public void writeDelete(Kml kml) throws KmlException {
		kml.println("<LineString" + getIdAndTargetIdFormatted(kml) + "></>");
	}
}