// Copyright 2021 Sebastian Kuerten
//
// This file is part of waldbrand-website.
//
// waldbrand-website is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// waldbrand-website is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with waldbrand-website. If not, see <http://www.gnu.org/licenses/>.

package de.waldbrand.app.website.pages.wes.maps;

import static de.waldbrand.app.website.lbforst.PoiUtil.not;

import java.io.IOException;

import org.jsoup.nodes.DataNode;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.Head;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.Script;
import de.topobyte.melon.commons.io.Resources;
import de.topobyte.webpaths.WebPath;
import de.waldbrand.app.website.Website;
import de.waldbrand.app.website.lbforst.WesType;
import de.waldbrand.app.website.lbforst.model.Poi;
import de.waldbrand.app.website.pages.base.SimpleBaseGenerator;
import de.waldbrand.app.website.pages.wes.WesAttributionUtil;
import de.waldbrand.app.website.util.MapUtil;
import de.waldbrand.app.website.util.MarkerShape;

public class WesMapGenerator extends SimpleBaseGenerator
{

	public WesMapGenerator(WebPath path)
	{
		super(path);
	}

	@Override
	protected void content() throws IOException
	{
		Head head = builder.getHead();
		MapUtil.head(head);

		content.ac(HTML.h2("Wasserentnahmestellen"));
		P p = content.ac(HTML.p());
		p.appendText("Kein Filter – alle WES");

		MapUtil.addMap(content);

		MapUtil.addMarkerDef(content, MarkerShape.CIRCLE, "red", "fa",
				"fa-tint");

		Script script = content.ac(HTML.script());
		StringBuilder code = new StringBuilder();

		MapUtil.markerStart(code);
		for (Poi poi : not(Website.INSTANCE.getData().getPois(),
				WesType.GEPLANT.getId())) {
			WesMapUtil.marker(code, poi, true, MapUtil.getDefaultMarkerId(),
					"markers");
		}
		MapUtil.markerEnd(code);
		script.ac(new DataNode(code.toString()));

		script = content.ac(HTML.script());
		script.ac(new DataNode(Resources.loadString("js/map-history.js")));

		WesAttributionUtil.attribution(content);
	}

}
