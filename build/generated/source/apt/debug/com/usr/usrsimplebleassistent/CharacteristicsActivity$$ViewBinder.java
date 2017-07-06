// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class CharacteristicsActivity$$ViewBinder<T extends com.usr.usrsimplebleassistent.CharacteristicsActivity> extends com.usr.usrsimplebleassistent.MyBaseActivity$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131230937, "field 'filterView'");
    target.filterView = view;
    view = finder.findRequiredView(source, 2131230828, "field 'lvCharacteristics'");
    target.lvCharacteristics = finder.castView(view, 2131230828, "field 'lvCharacteristics'");
    view = finder.findRequiredView(source, 2131230939, "field 'viewShadow'");
    target.viewShadow = view;
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.filterView = null;
    target.lvCharacteristics = null;
    target.viewShadow = null;
  }
}
