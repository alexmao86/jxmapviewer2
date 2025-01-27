
package org.jxmapviewer.viewer.bmng;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * @author joshy
 */
public class CylindricalProjectionTileFactory extends DefaultTileFactory {
	/**
	 * @param info
	 *            the tile factory info
	 */
	public CylindricalProjectionTileFactory(TileFactoryInfo info) {
		super(info);
	}

	@Override
	public Point2D geoToPixel(GeoPosition c, int zoom) {
		// calc the pixels per degree
		Dimension mapSizeInTiles = getMapSize(zoom);
		// double size_in_tiles = (double)getInfo().getMapWidthInTilesAtZoom(zoom);
		// double size_in_tiles = Math.pow(2, getInfo().getTotalMapZoom() - zoom);
		double size_in_pixels = mapSizeInTiles.getWidth() * getInfo().getTileSize(zoom);
		double ppd = size_in_pixels / 360;

		// the center of the world
		double centerX = this.getTileSize(zoom) * mapSizeInTiles.getWidth() / 2;
		double centerY = this.getTileSize(zoom) * mapSizeInTiles.getHeight() / 2;

		double x = c.getLongitude() * ppd + centerX;
		double y = -c.getLatitude() * ppd + centerY;

		return new Point2D.Double(x, y);
	}

	@Override
	public GeoPosition pixelToGeo(Point2D pix, int zoom) {
		// calc the pixels per degree
		Dimension mapSizeInTiles = getMapSize(zoom);
		double size_in_pixels = mapSizeInTiles.getWidth() * getInfo().getTileSize(zoom);
		double ppd = size_in_pixels / 360;

		// the center of the world
		double centerX = this.getTileSize(zoom) * mapSizeInTiles.getWidth() / 2;
		double centerY = this.getTileSize(zoom) * mapSizeInTiles.getHeight() / 2;

		double lon = (pix.getX() - centerX) / ppd;
		double lat = -(pix.getY() - centerY) / ppd;

		return new GeoPosition(lat, lon);
	}

	/*
	 * x = lat * ppd + fact x - fact = lat * ppd (x - fact)/ppd = lat y = -lat*ppd +
	 * fact -(y-fact)/ppd = lat
	 */
}
