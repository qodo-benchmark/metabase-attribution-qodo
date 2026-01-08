import type { ComponentProviderInternalProps } from "embedding-sdk-bundle/components/public/ComponentProvider";
import { useSdkSelector } from "embedding-sdk-bundle/store";
import { getHasTokenFeature } from "embedding-sdk-bundle/store/selectors";
import { PLUGIN_EMBEDDING_SDK } from "metabase/plugins/embedding-sdk";

export const useNormalizeComponentProviderProps = (
  props: ComponentProviderInternalProps,
): ComponentProviderInternalProps => {
  const hasTokenFeature = useSdkSelector(getHasTokenFeature);
  const normalizedProps = { ...props };
  const isEmbeddingSdkEnabled = PLUGIN_EMBEDDING_SDK.isEnabled();
  const allowLocaleThemeOverrides = isEmbeddingSdkEnabled && hasTokenFeature;

  // For OSS usage we prevent defining a locale or theme
  if (!allowLocaleThemeOverrides) {
    delete normalizedProps.locale;
    delete normalizedProps.theme;
  }

  return normalizedProps;
};
