// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent.views;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OptionsMenu$$ViewBinder<T extends com.usr.usrsimplebleassistent.views.OptionsMenu> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230842, "field 'rv_options'");
    target.rv_options = finder.castView(view, 2131230842, "field 'rv_options'");
  }

  @Override public void unbind(T target) {
    target.rv_options = null;
  }
}
