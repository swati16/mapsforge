/*
 * Copyright 2010, 2011, 2012, 2013 mapsforge.org
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.map.layer.queue;

import java.util.Collection;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.MapPosition;
import org.mapsforge.core.model.Tile;
import org.mapsforge.core.util.MercatorProjection;

final class QueueItemScheduler {
	static final double PENALTY_PER_ZOOM_LEVEL = 10 * Tile.TILE_SIZE;

	static <T extends Job> void schedule(Collection<QueueItem<T>> queueItems, MapPosition mapPosition) {
		for (QueueItem<T> queueItem : queueItems) {
			queueItem.setPriority(calculatePriority(queueItem.object.tile, mapPosition));
		}
	}

	private static double calculatePriority(Tile tile, MapPosition mapPosition) {
		double tileLatitude = MercatorProjection.tileYToLatitude(tile.tileY, tile.zoomLevel);
		double tileLongitude = MercatorProjection.tileXToLongitude(tile.tileX, tile.zoomLevel);

		int halfTileSize = Tile.TILE_SIZE / 2;
		double tilePixelX = MercatorProjection.longitudeToPixelX(tileLongitude, mapPosition.zoomLevel) + halfTileSize;
		double tilePixelY = MercatorProjection.latitudeToPixelY(tileLatitude, mapPosition.zoomLevel) + halfTileSize;

		LatLong latLong = mapPosition.latLong;
		double mapPixelX = MercatorProjection.longitudeToPixelX(latLong.longitude, mapPosition.zoomLevel);
		double mapPixelY = MercatorProjection.latitudeToPixelY(latLong.latitude, mapPosition.zoomLevel);

		double diffPixel = Math.hypot(tilePixelX - mapPixelX, tilePixelY - mapPixelY);
		int diffZoom = Math.abs(tile.zoomLevel - mapPosition.zoomLevel);

		return diffPixel + PENALTY_PER_ZOOM_LEVEL * diffZoom;
	}

	private QueueItemScheduler() {
		throw new IllegalStateException();
	}
}
