// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CharacteristicsAdapter$ViewHolder$$ViewBinder<T extends com.usr.usrsimplebleassistent.adapter.CharacteristicsAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230882, "field 'tvCharacteristicName'");
    target.tvCharacteristicName = finder.castView(view, 2131230882, "field 'tvCharacteristicName'");
    view = finder.findRequiredView(source, 2131230883, "field 'tvCharacteristicProperties'");
    target.tvCharacteristicProperties = finder.castView(view, 2131230883, "field 'tvCharacteristicProperties'");
  }

  @Override public void unbind(T target) {
    target.tvCharacteristicName = null;
    target.tvCharacteristicProperties = null;
  }
}
