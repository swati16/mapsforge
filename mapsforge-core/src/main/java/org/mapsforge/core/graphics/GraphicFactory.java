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
package org.mapsforge.core.graphics;

import java.io.IOException;
import java.io.InputStream;

public interface GraphicFactory {
	Bitmap createBitmap(InputStream inputStream) throws IOException;

	Bitmap createBitmap(int width, int height);

	Canvas createCanvas();

	int createColor(Color color);

	int createColor(int alpha, int red, int green, int blue);

	Matrix createMatrix();

	Paint createPaint();

	Path createPath();
}
