# Copyright (C) 2017 Intel Corporation
# Released under the MIT license (see COPYING.MIT)

import os
import sys
import glob
import re

from oeqa.core.context import OETestContext

class CompatLayerTestContext(OETestContext):
    def __init__(self, td=None, logger=None, layer=None, test_software_layer_signatures=True):
        super(CompatLayerTestContext, self).__init__(td, logger)
        self.layer = layer
        self.test_software_layer_signatures = test_software_layer_signatures
