DESCRIPTION = "Build Caffe library for CNN using OpenBLAS lib"
SUMMARY = "Caffe : A fast open framework for deep learning"
HOMEPAGE = "http://caffe.berkeleyvision.org/"
LICENSE = "BSD"
PRIORITY= "optional"
SECTION = "libs"
PR = "r0"

DEPENDS = " \
    make \
    boost \
    openblas \
    protobuf-native \
    protobuf \
    glog \
    gflags \
"

export STAGING_INCDIR_NATIVE
export STAGING_LIBDIR_NATIVE
export STAGING_INCDIR
export STAGING_LIBDIR

LIC_FILES_CHKSUM = "file://LICENSE;md5=91d560803ea3d191c457b12834553991"

SRCREV = "acbe051635d318fd367a5744fcfb57740b1f7b35"
SRC_URI = "git://github.com/jerryz123/riscv-caffe.git;branch=riscv"

S = "${WORKDIR}/git"


do_configure() {
           cp Makefile.config.example Makefile.config
           sed -i "/^INCLUDE_DIRS :=/c\INCLUDE_DIRS := ${STAGING_INCDIR}" Makefile.config
           sed -i "/^LIBRARY_DIRS :=/c\LIBRARY_DIRS := ${STAGING_LIBDIR}" Makefile.config
           sed -i "/Q ?= @/d" Makefile.config
           sed -i "/^$(DISTRIBUTE_DIR): all py | $(DISTRIBUTE_SUBDIRS)/c\$(DISTRIBUTE_DIR): all | $(DISTRIBUTE_SUBDIRS)" Makefile
}

do_compile () {
           oe_runmake BLAS=open CPU_ONLY=1 USE_OPENCV=0 USE_LEVELDB=0 USE_LMDB=0 CUSTOM_CXX="${TARGET_PREFIX}g++ ${TOOLCHAIN_OPTIONS}" WITH_PYTHON_LAYER=0
}
do_install () {
           oe_runmake BLAS=open CPU_ONLY=1 USE_OPENCV=0 USE_LEVELDB=0 USE_LMDB=0 CUSTOM_CXX="${TARGET_PREFIX}g++ ${TOOLCHAIN_OPTIONS}" WITH_PYTHON_LAYER=0 DISTRIBUTE_DIR=${D}/usr distribute
           rm -rf ${D}/usr/python
}
FILES_${PN} += " \
    ${prefix}/proto/* \
"

FILES_${PN}-dev = " \
    ${includedir} \
    ${datadir}/Caffe/*cmake \
    ${libdir}/*.so \
"