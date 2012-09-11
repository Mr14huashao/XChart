/**
 * Copyright 2011-2012 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xchart;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import com.xeiam.xchart.interfaces.IChartPart;

/**
 * Axis tick labels
 */
public class AxisTickLabels implements IChartPart {

  private AxisTick axisTick;

  /** the font */
  protected Font font;

  /** the bounds */
  private Rectangle bounds;

  /**
   * Constructor
   * 
   * @param axis the axis
   */
  protected AxisTickLabels(AxisTick axisTick) {

    this.axisTick = axisTick;
    font = new Font(Font.SANS_SERIF, Font.BOLD, 12); // default font
  }

  @Override
  public Rectangle getBounds() {

    return bounds;
  }

  @Override
  public void paint(Graphics2D g) {

    bounds = new Rectangle();

    g.setColor(axisTick.axis.axisPair.chart.fontColor);

    if (axisTick.axis.getDirection() == Axis.Direction.Y) { // Y-Axis

      int xOffset = (int) (axisTick.axis.getAxisTitle().getBounds().getX() + axisTick.axis.getAxisTitle().getBounds().getWidth());
      int yOffset = (int) (axisTick.axis.getPaintZone().getY());
      int maxTickLabelWidth = 0;
      for (int i = 0; i < axisTick.getTickLabels().size(); i++) {

        String tickLabel = axisTick.getTickLabels().get(i);
        int tickLocation = axisTick.getTickLocations().get(i);

        FontRenderContext frc = g.getFontRenderContext();
        // TextLayout layout = new TextLayout(tickLabel, font, new FontRenderContext(null, true, false));
        TextLayout layout = new TextLayout(tickLabel, font, frc);
        Rectangle tickLabelBounds = layout.getPixelBounds(null, 0, 0);
        layout.draw(g, xOffset, (int) (yOffset + axisTick.axis.getPaintZone().getHeight() - tickLocation + tickLabelBounds.getHeight() / 2.0));

        if (tickLabelBounds.getWidth() > maxTickLabelWidth) {
          maxTickLabelWidth = (int) tickLabelBounds.getWidth();
        }
      }

      // bounds
      bounds = new Rectangle(xOffset, yOffset, maxTickLabelWidth, (int) axisTick.axis.getPaintZone().getHeight());
      // g.setColor(Color.blue);
      // g.draw(bounds);

    } else { // X-Axis

      int xOffset = (int) (axisTick.axis.getPaintZone().getX());
      int yOffset = (int) (axisTick.axis.getAxisTitle().getBounds().getY());
      int maxTickLabelHeight = 0;
      for (int i = 0; i < axisTick.getTickLabels().size(); i++) {

        String tickLabel = axisTick.getTickLabels().get(i);
        int tickLocation = axisTick.getTickLocations().get(i);

        FontRenderContext frc = g.getFontRenderContext();
        TextLayout layout = new TextLayout(tickLabel, font, frc);
        Rectangle tickLabelBounds = layout.getPixelBounds(null, 0, 0);
        layout.draw(g, (int) (xOffset + tickLocation - tickLabelBounds.getWidth() / 2.0), yOffset);

        if (tickLabelBounds.getHeight() > maxTickLabelHeight) {
          maxTickLabelHeight = (int) tickLabelBounds.getHeight();
        }
      }

      // bounds
      bounds = new Rectangle(xOffset, yOffset - maxTickLabelHeight, (int) axisTick.axis.getPaintZone().getWidth(), maxTickLabelHeight);
      // g.setColor(Color.blue);
      // g.draw(bounds);

    }

  }
}
